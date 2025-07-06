import 'package:dio/dio.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../utils/logger.dart';
import '../utils/global.dart';
import '../utils/auth.dart';

class Request {
  static final Dio _dio = Dio(BaseOptions(
    baseUrl: dotenv.env['API_ROOT'] ?? 'http://localhost:9999',
    connectTimeout: const Duration(seconds: 600),
    receiveTimeout: const Duration(seconds: 600),
  ));

  static void init() {
    _dio.interceptors.clear();

    _dio.interceptors.add(InterceptorsWrapper(
      onRequest: (options, handler) async {
        final prefs = await SharedPreferences.getInstance();
        final token = prefs.getString('Admin-Token');
        if (token != null) {
          options.headers['Authorization'] = token;
        }
        handler.next(options);
      },
      onResponse: (response, handler) {
        final res = response.data;
        if (res is Map && res.containsKey('status')) {
          final status = res['status'];
          if ([200, 400, 601, 602, 404, 423].contains(status)) {
            handler.resolve(response);
          } else {
            handler.reject(DioException(
              requestOptions: response.requestOptions,
              response: response,
              message: res['msgContent'] ?? 'サーバーエラー',
              type: DioExceptionType.badResponse,
            ));
          }
        } else {
          handler.resolve(response);
        }
      },
      onError: (DioException e, handler) async {
        if (e.response?.statusCode == 401) {
          final prefs = await SharedPreferences.getInstance();
          await prefs.clear();
          await Auth.removeAuth();
          logger.e('401: Token expired, should logout.');

          if (navigatorKey.currentState?.canPop() ?? false) {
            navigatorKey.currentState!.pushNamedAndRemoveUntil('/login', (route) => false);
          }
        }
        handler.next(e);
      },
    ));
  }

  static Dio get instance => _dio;
}
