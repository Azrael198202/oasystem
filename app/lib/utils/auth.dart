import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';

class Auth {
  static const String _tokenKey = 'Admin-Token';
  static const String _userInfoKey = 'userInfo-key';
  static const String _userDetailedInfoKey = 'userDetailedInfo-key';

  static Future<void> setAuthInfo(String token, Map<String, dynamic> userInfo) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('Admin-Token', token);
    await prefs.setString('userInfo-key', jsonEncode(userInfo));
  }


  static Future<void> setToken(String token) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_tokenKey, token);
  }

  static Future<String?> getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString(_tokenKey);
  }

  static Future<void> setUserInfo(Map<String, dynamic> userInfo) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString(_userInfoKey, jsonEncode(userInfo));
  }

  static Future<Map<String, dynamic>?> getUserInfo() async {
    final prefs = await SharedPreferences.getInstance();
    final str = prefs.getString(_userInfoKey);
    if (str == null) return null;
    return jsonDecode(str);
  }

  static Future<void> removeAuth() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove(_tokenKey);
    await prefs.remove(_userInfoKey);
    await prefs.remove(_userDetailedInfoKey);
  }
}
