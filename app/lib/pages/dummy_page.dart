import 'package:flutter/material.dart';

class DummyPage extends StatelessWidget {
  final String title;
  const DummyPage({super.key, required this.title});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text('$title ページ', style: const TextStyle(fontSize: 20)),
    );
  }
}
