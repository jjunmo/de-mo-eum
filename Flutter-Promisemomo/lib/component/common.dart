// Ctrl + Alt + m
import 'package:flutter/material.dart';

AppBar commonAppBar({required String titleName, String? addTitle, bool backButtonFlag = false}) {

  String title = "";


  if (addTitle != null) {
    title = "$titleName $addTitle";
  } else {
    title = titleName;
  }

  return AppBar(
    title: Row(
      mainAxisAlignment: backButtonFlag ? MainAxisAlignment.spaceBetween : MainAxisAlignment.start,
      children: [

        backButtonFlag
        ? TextButton(
            onPressed: () {
              print("TEXT BUTTON EVENT");
            },
            child: const Text(
                '<',
                style: TextStyle(fontSize: 20,color: Colors.white)
            ),
          )
        : Container(),

        Text(title),
      ],
    ),
  );
}