import 'package:flutter/material.dart';
import '../api/login_api.dart';
import '../utils/auth.dart';
import '../utils/string_utils.dart';
import '../utils/logger.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  final _idController = TextEditingController();
  final _pwController = TextEditingController();
  bool loading = false;

  void _handleLogin() async {
    if (!_formKey.currentState!.validate()) return;

    setState(() => loading = true);

    await Future.delayed(const Duration(seconds: 1));

    if (!mounted) return;

    final userId = _idController.text.trim().right(3).padLeft(3, '0');
    final companyid = _idController.text.trim().left(6).padLeft(6, '0');
    final passWord = _pwController.text.trim();

    final loginData = {
      'companyCode': companyid,
      'userId': userId,
      'passWord': passWord
    };

    try {
      final res = await LoginApi.agentLogin(loginData);

      if (!mounted) return;

      if (res['status'] == 400) {
        _showSnackBar('社員番号またはパスワードが正しくありません');
      } else if (res['status'] == 602) {
        _showSnackBar('社員番号：$userId は存在しません。');
      } else {
        final loginInfo = res['data'];
        final token = loginInfo['token'];
        final userInfo = loginInfo['loginUserInfo'];

        await Auth.setAuthInfo(token, userInfo);

        _showSnackBar('ログイン成功');
        if (!mounted) return;
        Navigator.pushReplacementNamed(context, '/toppage');
      }
    } catch (e) {
      _showSnackBar('ログインに失敗しました');
      logger.e('Login error: $e');
    }

    if (!mounted) return;
    setState(() => loading = false);
  }

  void _showSnackBar(String message) {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(message)));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: Form(
            key: _formKey,
            child: Column(
              children: [
                Image.asset('assets/images/logo.png', height: 80),
                const SizedBox(height: 32),
                TextFormField(
                  controller: _idController,
                  decoration: const InputDecoration(labelText: '社員番号'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return '社員番号は必須入力です。';
                    }
                    else if (value.length != 9) {
                      return '社員番号は9桁を入力してください。';
                    }
                    return null;
                  },
                ),
                TextFormField(
                  controller: _pwController,
                  decoration: const InputDecoration(labelText: 'パスワード'),
                  obscureText: true,
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'パスワードは必須入力です。';
                    } else if (value.length < 6 || value.length > 20) {
                      return '６～２０桁を入力してください。';
                    }
                    return null;
                  },
                ),
                const SizedBox(height: 24),
                ElevatedButton(
                  onPressed: loading ? null : _handleLogin,
                  child: loading
                      ? const SizedBox(
                          height: 18,
                          width: 18,
                          child: CircularProgressIndicator(strokeWidth: 2),
                        )
                      : const Text('ログイン'),
                ),
                TextButton(
                  onPressed: () {
                    Navigator.pushNamed(context, '/registered?step=1');
                  },
                  child: const Text('パスワードをお忘れの方'),
                ),
                const SizedBox(height: 16),
                const Text('お問い合わせはこちらまで'),
                const Text(
                  'abc@abc.co.jp',
                  style: TextStyle(color: Colors.blue),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
