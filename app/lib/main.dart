import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'router/app_router.dart';
import 'utils/request.dart';
import 'utils/global.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized(); 
  await dotenv.load(fileName: ".env");
  Request.init();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: navigatorKey,
      title: 'OA Login',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.blue,
        scaffoldBackgroundColor: Colors.white,
        inputDecorationTheme: const InputDecorationTheme(
          border: UnderlineInputBorder(),
        ),
      ),
      initialRoute: '/login',
      routes: AppRouter.routes,
    );
  }
}
