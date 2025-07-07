import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../utils/global.dart';

class AppDrawer extends StatelessWidget {
  const AppDrawer({super.key});
  

  Future<void> _logout(BuildContext context) async {

    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('ログアウト確認'),
        content: const Text('ログアウトしてもよろしいですか？'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('キャンセル'),
          ),
          TextButton(
            onPressed: () async {
              Navigator.pop(context);    // Close the dialog
              Navigator.of(context).pop(); // Close the drawer
              final prefs = await SharedPreferences.getInstance();
              await prefs.clear();     // Clear shared preferences
              navigatorKey.currentState?.pushNamedAndRemoveUntil('/login', (_) => false);
            },
            child: const Text('ログアウト'),
          ),
        ],
      ),
    );
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
            ExpansionTile(  // 👇 这部分是你新增的子菜单
              leading: Icon(Icons.settings, color: Colors.indigo[900]),
              title: Text('各種設定', style: TextStyle(color: Colors.indigo[900])),
              iconColor: Colors.indigo[900],
              collapsedIconColor: Colors.indigo[900],
              children: [
                _subDrawerItem(context, text: 'アカウント設定', route: '/account'),
                _subDrawerItem(context, text: 'メール設定', route: '/mail'),
                _subDrawerItem(context, text: 'プッシュ通知', route: '/push'),
              ],
            ),
            _drawerItem(icon: Icons.support_agent, text: 'サポートと利用規約', showArrow: true),
            _drawerItem(
              icon: Icons.lock,
              text: 'ログアウト',
              onTap: () => _logout(context),
            ),
            const Spacer(),
            const Padding(
              padding: EdgeInsets.all(16),
              child: Text('version 0.0.1', style: TextStyle(color: Colors.grey)),
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

  Widget _subDrawerItem(BuildContext context, {required String text, required String route}) {
    return ListTile(
      contentPadding: const EdgeInsets.only(left: 72, right: 16),
      title: Text(text, style: const TextStyle(fontSize: 14)),
      onTap: () {
        Navigator.of(context).pop(); /// Close the drawer
        navigatorKey.currentState?.pushNamed(route);
      },
    );
  }
}
