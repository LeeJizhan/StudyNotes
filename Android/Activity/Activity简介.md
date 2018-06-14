## 什么是 Activity
---
>Activity 是一个应用组件，安卓四大组件之一（安卓四大组件包括 Activity、Service、Broadcast Receiver 和 Content Provider），其余3个组件后面再提。Activity 提供了一个屏幕与用户进行交互，来执行比如拨打电话、拍照、发邮件等操作，类似于一个网页。每个 Activity 都会获得一个用于绘制其用户界面的窗口（由 Window Manager 管理），窗口通常充满屏幕，但是也可以小于屏幕并浮动在其他窗口之上。一个应用程序通常由很多个彼此松散联系的（低耦合）Activity 组成，固定的有一个所谓的“MainActivity”，可以在清单文件 AndroidManifest.xml 进行更改，每个 Activity 都需要在 清单文件
 AndroidManifest.xml 中进行声明。
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="你的应用程序包名">

    <application
        <!-- android:allowBackup设置为true意味着为应用程序数据的备份和恢复功能-->
        <!--用户即可通过adb backup和adb restore来进行对应用数据的备份和恢复-->
        android:allowBackup="true"
        android:icon="你的应用程序图标"
        android:label="你的应用程序名字"
        <!--android:supportsRtl设置为true意味着可以强制使用从右到左布局-->
        android:supportsRtl="true"
        android:theme="你的应用程序主题">
        <!--"主" Activity 应用首先启动的 Activity-->
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <!-- 另一个 Activity 由"主" Activity 或其他 Activity 启动-->
        <activity android:name=".Main2Activity"></activity>
    </application>

</manifest>
```
---
## 创建 Activity
---
>要创建一个 Activity，必须要继承 Activity 或者它的子类（在Android Studio 中新建一个空的 Activity 默认继承 AppCompatActivity），并且必须实现它的 onCreate() 方法，并用 setContentView() 为其指定一个布局。
 ```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
```
---
## 启动 Activity
---
启动 Activity 的方法有两种，startActivity() 和 startActivityForResult()。
1. startActivity()
>startActivity() 比较简单，直接传入一个 Intent 对象就可以跳转。
```
    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
    startActivity(intent);
```
2. startActivityForResult()
>有时候我们希望在 Activity 之间传递数据，这时候就要用到 startActivityForResult()了。

由 MainActivity 向 Main2Activity 直接传送数据
MainActivity.java
```
    //创建一个 Intent 对象
    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
    //向 Intent 中传入一个键值对
    intent.putExtra("text1",et_name.getText().toString());
    //跳转
    startActivityForResult(intent,ConstantUtils.REQUEST_CODE);
```
Main2Activity.java
```
    //获取刚刚传过来的 Intent 对象
    Intent intent = getIntent();
    //利用 key 获取值
    String text = intent.getStringExtra("text1");
    //显示出来
    tv_text2.setText(text);
```
图片展示
![1.jpg](http://upload-images.jianshu.io/upload_images/10539135-b8c6b94081f144f4.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)![2.jpg](http://upload-images.jianshu.io/upload_images/10539135-e9ec2145971dc951.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

由 MainActivity 发送请求码，获取 Main2Activity 返回的数据
Main2Activity.java
```
    //创建一个 Intent 对象
    Intent intent = new Intent();
    // 获取输入的值
    String text = et_name.getText().toString();
    //将输入的值传入Intent
    intent.putExtra("text2",text);
    //设置结果码
    setResult(ConstantUtils.RESULT_CODE,intent);
```
MainActivity,java
 ```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantUtils.REQUEST_CODE && resultCode == ConstantUtils.RESULT_CODE){
            String name = data.getStringExtra("text2");
            tv_name.setText(name);
        }
    }
```
图片展示
![3.jpg](http://upload-images.jianshu.io/upload_images/10539135-a557b49d1660d32b.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)![4.jpg](http://upload-images.jianshu.io/upload_images/10539135-105525060a4e4be2.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## 销毁Activity
>您可以通过调用 Activity 的 finish() 方法来结束该 Activity。您还可以通过调用 finishActivity(int) 结束您之前启动的另一个 Activity。







