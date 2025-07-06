import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return ListView(
      padding: const EdgeInsets.all(16),
      children: [
        _buildQuickAccessRow(),
        const SizedBox(height: 16),
        _buildBlueBanner(),
        const SizedBox(height: 16),
        _buildProfileCard(),
      ],
    );
  }

  Widget _buildQuickAccessRow() {
    return Column(
      children: [
        Row(
          children: [
            _buildQuickCard('気になる受信', '新着 5件'),
            const SizedBox(width: 8),
            _buildQuickCard('スカウト受信', '新着 13件'),
          ],
        ),
        const SizedBox(height: 8),
        Row(
          children: [
            _buildQuickCard('転職希望職種', '良い仕事があれば'),
            const SizedBox(width: 8),
            _buildQuickCard('ブロック企業の登録', '登録済み'),
          ],
        ),
      ],
    );
  }

  Widget _buildQuickCard(String title, String subtitle) {
    return Expanded(
      child: Card(
        elevation: 2,
        child: ListTile(
          title: Text(title, style: const TextStyle(fontSize: 14)),
          subtitle: Text(
            subtitle,
            style: const TextStyle(color: Colors.orange, fontSize: 12),
          ),
          trailing: const Icon(Icons.arrow_forward_ios, size: 14),
          onTap: () {
            // TODO: 快捷卡片点击处理
          },
        ),
      ),
    );
  }

  Widget _buildBlueBanner() {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.blue[700],
        borderRadius: BorderRadius.circular(8),
      ),
      child: const Row(
        children: [
          Expanded(
            child: Text(
              '転職アドバイザーが限定求人をご紹介します',
              style: TextStyle(color: Colors.white, fontSize: 14),
            ),
          ),
          Icon(Icons.arrow_forward_ios, color: Colors.white, size: 16),
        ],
      ),
    );
  }

  Widget _buildProfileCard() {
    return Card(
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'プロフィールの登録',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
            ),
            const SizedBox(height: 12),
            const Text('プロフィール入力率 88%'),
            const SizedBox(height: 6),
            const LinearProgressIndicator(value: 0.88),
            const SizedBox(height: 12),
            const Text(
              'さらにプロフィールを充実させることで\nスカウトやマッチ率に繋がります。',
              style: TextStyle(fontSize: 13),
            )
          ],
        ),
      ),
    );
  }
}
