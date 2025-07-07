import 'package:flutter/material.dart';
import '../pages/login_page.dart';
import '../pages/home_main.dart';
import '../pages/account/account_setting_page.dart';

class AppRouter {
  static Map<String, WidgetBuilder> routes = {
    '/login': (context) => const LoginPage(),
    '/toppage': (context) => const HomeMainPage(),
    '/account': (context) => const AccountSettingPage(),
    // 添加更多页面时扩展
  };
}
