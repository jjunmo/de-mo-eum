import 'package:flutter/material.dart';

import 'component/common.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.brown,
      ),
      home: const MyHomePage(),
    );
  }
}



class MyHomePage extends StatelessWidget {

  const MyHomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: commonAppBar(titleName: 'Flutter Demo Home Page', backButtonFlag: true),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
              style: TextStyle(fontSize: 50),
            ),

            const Text(
              'Hi',
            ),

            customRow(),

            Text(
              // _counter.toString(),
              '0',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      floatingActionButton: myPlusButton(), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }



  FloatingActionButton myPlusButton() {
    return FloatingActionButton(
      onPressed: (){

      },
      tooltip: 'Increment',
      child: const Icon(Icons.add),
    );
  }

  Row customRow() {
    return Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [

              Container(
                // padding:
                margin: const EdgeInsets.symmetric(horizontal: 3),
                child: const Text(
                  'dd',
                ),
              ),

              // const Padding(
              //     padding: EdgeInsets.symmetric(horizontal: 3)
              // ),

              const Text(
                'ff',
              ),

            ],
          );
  }
}



