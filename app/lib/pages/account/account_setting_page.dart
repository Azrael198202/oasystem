import 'package:flutter/material.dart';

class AccountSettingPage extends StatefulWidget {
  const AccountSettingPage({super.key});

  @override
  State<AccountSettingPage> createState() => _AccountSettingPageState();
}

class _AccountSettingPageState extends State<AccountSettingPage> {
  bool allowById = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('個人情報設定'),
        centerTitle: true,
        leading: BackButton(),
        actions: const [CloseButton()],
      ),
      body: ListView(
        children: [
          _buildHeader(),
          _buildTile(title: '表示名', value: '韓 鷹', onTap: () {}),
          _buildTile(title: '状態メッセージ', value: '頑張って', onTap: () {}),
          _buildTile(title: '電話番号', value: '+81 80-3378-6655', onTap: () {}),
          _buildLineIdSection(),
          _buildSwitchTile(),
          _buildTile(title: 'QRコード', onTap: () {}),
          _buildTile(title: '誕生日', value: 'Not set', onTap: () {}),
        ],
      ),
    );
  }

  Widget _buildHeader() {
    return Stack(
      children: [
        Image.asset(
          'assets/images/bg.jpg',
          height: 150,
          width: double.infinity,
          fit: BoxFit.cover,
        ),
        Positioned(
          right: 10,
          bottom: 10,
          child: CircleAvatar(
            backgroundColor: Colors.white70,
            child: const Icon(Icons.camera_alt),
          ),
        ),
        Positioned(
          left: 20,
          bottom: -30,
          child: Stack(
            alignment: Alignment.bottomRight,
            children: [
              const CircleAvatar(
                radius: 40,
                backgroundImage: AssetImage('assets/images/man.png'),
              ),
              CircleAvatar(
                radius: 14,
                backgroundColor: Colors.white70,
                child: const Icon(Icons.camera_alt, size: 16),
              )
            ],
          ),
        ),
        const SizedBox(height: 80),
      ],
    );
  }

  Widget _buildTile({required String title, String? value, VoidCallback? onTap}) {
    return ListTile(
      title: Text(title),
      subtitle: value != null ? Text(value) : null,
      trailing: const Icon(Icons.chevron_right),
      onTap: onTap,
    );
  }

  Widget _buildLineIdSection() {
    return ListTile(
      title: const Text('アカウント ID'),
      subtitle: const Text('azrael198202'),
      trailing: TextButton(
        onPressed: () {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Copied')),
          );
        },
        child: const Text('Copy'),
      ),
    );
  }

  Widget _buildSwitchTile() {
    return SwitchListTile(
      title: const Text('Allow others to add me by ID'),
      value: allowById,
      onChanged: (value) => setState(() => allowById = value),
    );
  }
}
