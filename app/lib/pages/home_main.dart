import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:badges/badges.dart' as badges;

import '../api/notification_api.dart';
import '../components/app_bottom_nav.dart';
import '../utils/logger.dart';
import 'home_page.dart';
import 'dummy_page.dart';
import '../components/app_drawer.dart';

class HomeMainPage extends StatefulWidget {
  const HomeMainPage({super.key});

  @override
  State<HomeMainPage> createState() => _HomeMainPageState();
}

class _HomeMainPageState extends State<HomeMainPage> {
  int _selectedIndex = 0;
  int _notificationCount = 0;

  final List<Widget> _pages = const [
    HomePage(),
    DummyPage(title: '相談'),
    DummyPage(title: 'マイページ'),
  ];

  @override
  void initState() {
    super.initState();
    _restoreTabIndex();
    _loadNotificationCount();
  }

  Future<void> _restoreTabIndex() async {
    final prefs = await SharedPreferences.getInstance();
    final saved = prefs.getInt('selectedTab') ?? 0;
    setState(() {
      _selectedIndex = saved;
    });
  }

  void _onTabTapped(int index) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt('selectedTab', index);
    setState(() => _selectedIndex = index);
  }

  Future<void> _loadNotificationCount() async {
    try {
      final count = await NotificationApi.getUnreadCount();
      if (mounted) {
        setState(() => _notificationCount = count);
      }
    } catch (e) {
      logger.w('通知件数の取得に失敗: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('ホーム'),
        centerTitle: true,
        leading: Builder(
          builder: (context) => IconButton(
            icon: const Icon(Icons.menu),
            onPressed: () => Scaffold.of(context).openDrawer(),
          ),
        ),

        actions: [
          Padding(
            padding: const EdgeInsets.only(right: 16),
            child: badges.Badge(
              showBadge: _notificationCount > 0,
              badgeContent: Text(
                '$_notificationCount',
                style: const TextStyle(color: Colors.white, fontSize: 10),
              ),
              position: badges.BadgePosition.topEnd(top: -6, end: -6),
              child: IconButton(
                icon: const Icon(Icons.notifications_none),
                onPressed: () {
                  setState(() {
                    _notificationCount = 0; // 清除提示
                  });
                },
              ),
            ),
          ),
        ],
      ),
      drawer: const AppDrawer(),
      body: _pages[_selectedIndex],
      bottomNavigationBar: AppBottomNav(
        currentIndex: _selectedIndex,
        onTap: _onTabTapped,
      ),
    );
  }
}
