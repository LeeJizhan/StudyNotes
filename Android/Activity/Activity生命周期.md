## Activity 生命周期
通过实现回调方法管理 Activity 的生命周期对开发强大而又灵活的应用至关重要。 Activity 的生命周期会直接受到 Activity 与其他 Activity、其任务及返回栈的关联性的影响。
### 状态图
![image.png](http://upload-images.jianshu.io/upload_images/10539135-68984d6623d9b937.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### 解读状态图
1. 首次创建 Activity 时回调 onCreate()、onStart() 和 onResume() 方法进入运行态。一般在 onCreate() 方法中执行所有正常的静态设置 — 创建视图、将数据绑定到列表等等。 系统向此方法传递一个 Bundle 对象可以用于还原 Activity 的状态。不过一般是使用 onSaveInstanceState() 和 onRestoreInstanceState()。
2. 当另一个 Activity 出现在当前 Activity 的上方，但是又未完全覆盖当前 Activity 或者有一个对话框在当前 Activity 的上方时，Activity 回调 onPause() 方法，进入暂停态。还原时回调 onResume() 重新进入运行态。也有可能在系统需要内存时被 finish 掉，这时需要重新创建 Activity。
3. 当另一个 Activity 出现在当前 Activity 的上方，并且当前 Activity 完全不可见时，回调 onPause() 和 onStop() 方法，进入停止态。还原时回调 onRestart()，onStart() 和 onResume() 方法。和暂停态一样，也有可能在系统需要内存时被 finish 掉，这时需要重新创建 Activity。
4. 当 Activity 被 finish 或者系统销毁时，回调 onDestroy() 方法，Activity 的生命周期结束。
- - -
### Activity 基本以三种状态存在
1. Running
>此 Activity 位于屏幕前台并具有用户焦点。
2. onPause()
> 另一个 Activity 位于屏幕前台并具有用户焦点，但当前 Activity 仍可见。例如，另一个 Activity 显示在此 Activity 上方，并且该 Activity 部分透明或未覆盖整个屏幕。处于暂停状态的 Activity 处于完全活动状态，即对象保留在内存中，它保留了所有状态和成员信息，并与窗口管理器保持连接），但在内存极度不足的情况下，可能会被系统终止。
3. onStop()
> 当前 Activity 被另一个 Activity 完全遮盖（该 Activity 目前位于“后台”）。 处于停止状态的 Activity 同样仍处于活动状态（对象保留在内存中，它保留了所有状态和成员信息，但未与窗口管理器连接）。它对用户不再可见，在他处需要内存时可能会被系统终止。

系统处于暂停或停止状态时，系统可以要求其结束（finish掉），或者直接结束与该 Activity 有关的进程，将其从内存删除。Activity 被终止后必须重新 onCreate()。
- - -
## 实现 Activity 的回调
```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("Activity生命周期","onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Activity生命周期","onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Activity生命周期","onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Activity生命周期","onResume()");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Activity生命周期","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Activity生命周期","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Activity生命周期","onDestroy()");
    }
```
> Activity 的 ***整个生命周期*** 发生在  onCreate() 调用与 onDestroy() 调用之间。您的 Activity 应在 onCreate() 中执行“全局”状态设置（例如定义布局），并在 onDestroy() 中释放所有其余资源。例如，如果您的 Activity 有一个在后台运行的线程，用于从网络上下载数据，它可能会在 onCreate()中创建该线程，然后在 onDestroy() 中停止该线程。

> Activity 的 ***可见生命周期*** 发生在 onStart() 调用与 onStop() 调用之间。在这段时间，用户可以在屏幕上看到 Activity 并与其交互。例如，当一个新 Activity 启动，并且此 Activity 不再可见时，系统会调用onStop() 。您可以在调用这两个方法之间保留向用户显示 Activity 所需的资源。 例如，您可以在 onStart() 中注册一个 BroadcastReceiver 以监控影响 UI 的变化，并在用户无法再看到您显示的内容时在 onStop() 中将其取消注册。在 Activity 的整个生命周期，当 Activity 在对用户可见和隐藏两种状态中交替变化时，系统可能会多次调用 onStart() 和  onStop()。

> Activity 的 ***前台生命周期*** 发生在 onResume() 调用与 onPause() 调用之间。在这段时间，Activity 位于屏幕上的所有其他 Activity 之前，并具有用户焦点。Activity 可频繁转入和转出前台 。例如，当设备转入休眠状态或出现对话框时，系统会调用 onPause()。 由于此状态可能经常发生转变，因此这两个方法中应采用适度轻量级的代码，以避免因转变速度慢而让用户等待。






