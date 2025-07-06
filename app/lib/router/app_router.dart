import 'package:flutter/material.dart';
import '../pages/login_page.dart';
import '../pages/home_main.dart';

class AppRouter {
  static Map<String, WidgetBuilder> routes = {
    '/login': (context) => const LoginPage(),
    '/toppage': (context) => const HomeMainPage(),
    // 添加更多页面时扩展
  };
}
