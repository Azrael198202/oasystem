import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../utils/global.dart';

class AppDrawer extends StatelessWidget {
  const AppDrawer({super.key});

  Future<void> _logout(BuildContext context) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear();
    navigatorKey.currentState?.pushNamedAndRemoveUntil('/login', (_) => false);
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      backgroundColor: Colors.white,
      child: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 16, vertical: 12),
              child: Text('17:33', style: TextStyle(fontSize: 18)),
            ),
            _drawerItem(icon: Icons.history, text: '閲覧履歴'),
            _drawerItem(icon: Icons.settings, text: '各種設定', showArrow: true),
            _drawerItem(icon: Icons.support_agent, text: 'サポートと利用規約', showArrow: true),
            _drawerItem(
              icon: Icons.lock,
              text: 'ログアウト',
              onTap: () => _logout(context),
            ),
            const Spacer(),
            const Padding(
              padding: EdgeInsets.all(16),
              child: Text('version 2.1.7', style: TextStyle(color: Colors.grey)),
            ),
          ],
        ),
      ),
    );
  }

  Widget _drawerItem({
    required IconData icon,
    required String text,
    bool showArrow = false,
    VoidCallback? onTap,
  }) {
    return ListTile(
      leading: Icon(icon, color: Colors.indigo[900]),
      title: Text(text, style: TextStyle(color: Colors.indigo[900])),
      trailing: showArrow ? const Icon(Icons.keyboard_arrow_right) : null,
      onTap: onTap ?? () {},
    );
  }
}
