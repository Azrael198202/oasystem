import '../utils/request.dart';

class LoginApi {
  static Future<Map<String, dynamic>> agentLogin(Map<String, dynamic> data) async {
    final res = await Request.instance.post('/login/login.do', data: data);
    return res.data;
  }
}
