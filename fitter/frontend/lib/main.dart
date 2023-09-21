import 'package:fitter/screens/login_screen.dart';

import 'package:flutter/material.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:kakao_flutter_sdk/kakao_flutter_sdk.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await initializeDateFormatting();

  KakaoSdk.init(
    nativeAppKey: "db6c538af12e033ffc7eb3bbb87fc646",
  );
  runApp(const App());
}

class App extends StatelessWidget {
  const App({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      // home: AdditionalInfo(),
      // home: AdditionalBox(
      //     nickname: "nickname",
      //     email: "email",
      //     profileImagePath: "profileImagePath",
      //     age: "age",
      //     gender: "gender")
      home: LoginScreen(),
    );
  }
}
