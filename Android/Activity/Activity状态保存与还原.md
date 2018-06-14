## Activity 的状态保存与还原
- - -
#### Activity 的状态保存
> 一般来说，当 Activity 暂停或停止（onPause() 或 onStop() Activity 不再处于前台）时，Activity 的状态会得到保留。因为当Activity 暂停或停止时，对象仍保留在内存中 — 有关其成员和当前状态的所有信息仍处于活动状态。 因此，用户在 Activity 内所做的任何更改都会得到保留，这样一来，当 Activity 返回前台（当它“继续”）时，这些更改仍然存在。**但是**，当系统内存不足，为了恢复内存而销毁某个 Activity 时，Activity 的对象也会被销毁。因此系统在继续 Activity 时根本无法让其状态保持完好，而是必须在用户返回 Activity 时重建 Activity 对象（重新 onCreate() ）。但用户并不知道系统销毁 Activity 后又对其进行了重建，因此他们很可能认为 Activity 状态毫无变化，这时可能会把用户希望保存的信息也一起销毁掉，导致不好的用户体验。为了处理这种情况，Android 为我们提供了一个 onSaveInstanceState() 的方法，我们可以回调这个方法来保存一些用户希望保存的信息。在 Activity 变得易于销毁，所谓的易于销毁，在Android 的官方文档中提到，onSaveInstanceState() 这个方法是在 onPause() 之前或者之后但肯定是在 onStop() 之前执行的，就是还未销毁之前，系统会先回调onSaveInstanceState() 。

使用情景
(1)当用户按下HOME键时。
(2)选择运行其他的程序时。
(3)按下电源按键（关闭屏幕显示）时。
(4)从 Activity A 中启动另一个 Activity 时。
(5)屏幕方向切换时，例如从竖屏切换到横屏时。

来看看 onSaveInstanceState() 的使用
```
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /**
         * 可以在次使用 Bundle 的一系列方法保存信息
         * 比如:
         *     outState.putInt();
         */
    }
```
从上面的代码可以发现这里传入的是一个 Bundle 对象，可以在里面使用putInt()、putString()等一系列方法保存信息。

再来看下 onSaveInstanceState() 的源码
```
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle(WINDOW_HIERARCHY_TAG, mWindow.saveHierarchyState());
        Parcelable p = mFragments.saveAllState();
        if (p != null) {
            outState.putParcelable(FRAGMENTS_TAG, p);
        }
        getApplication().dispatchActivitySaveInstanceState(this, outState);
    }
```
由上面代码可知，outState 通过 put 一个 TAG 给mWindow.saveHierarchyState()。

继续跟踪，由于 Window 是抽象类，查看其子类PhoneWindow的saveHierarchyState()方法。
```
     /** {@inheritDoc} */
    @Override
    public Bundle saveHierarchyState() {
        Bundle outState = new Bundle();
        if (mContentParent == null) {
            return outState;
        }

        SparseArray<Parcelable> states = new SparseArray<Parcelable>();
        mContentParent.saveHierarchyState(states);
        outState.putSparseParcelableArray(VIEWS_TAG, states);
        // 代码到此即可知道 Bundle 是怎么保存的
        /**
        * 此处省略一系列代码
        */
        return outState;
    }
```
在上面代码中：
1. Bundle outState = new Bundle(); 初始化了一个 Bundle 对象。
2. mContentParent 是一个 ViewGroup 对象的实例，当它为空时，就直接返回一个空的 Bundle。
3. SparseArray<Parcelable> states = new SparseArray<Parcelable>(); states最终存到outState中。（Bundle 对象实现了 Parcelable 接口）
4. 继续跟踪 mContentParent.saveHierarchyState(states);
```
    public void saveHierarchyState(SparseArray<Parcelable> container) {
        dispatchSaveInstanceState(container);
    }
```
上面代码是 View.java 里面的，看不出什么，继续跟踪，看看它的dispatchSaveInstanceState() 方法。
```
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        if (mID != NO_ID && (mViewFlags & SAVE_DISABLED_MASK) == 0) {
            mPrivateFlags &= ~PFLAG_SAVE_STATE_CALLED;
            Parcelable state = onSaveInstanceState();
            if ((mPrivateFlags & PFLAG_SAVE_STATE_CALLED) == 0) {
                throw new IllegalStateException(
                        "Derived class did not call super.onSaveInstanceState()");
            }
            if (state != null) {
                // Log.i("View", "Freezing #" + Integer.toHexString(mID)
                // + ": " + state);
                container.put(mID, state);
            }
        }
    }
```
从上面的代码：
首先要搞清楚里面的一些常量是什么
1）NO_ID
```
    /**
     * Used to mark a View that has no ID.
     */
    public static final int NO_ID = -1;
```
    由注释可知它是用来标志一个没有 id 的 View 。
2）mID
```
    @IdRes
    @ViewDebug.ExportedProperty(resolveId = true)
    int mID = NO_ID;

```
```
    public void setId(@IdRes int id) {
        mID = id;
        if (mID == View.NO_ID && mLabelForId != View.NO_ID) {
            mID = generateViewId();
        }
    }
```
    每个 View 都有它的 ID ，要么在 xml 中指定，要么在代码中setID()，要么是默认 ID。告诫大家尽量别用同样的 ID 命名 View ，否则会有意想不到的 Bug。
3）PFLAG_SAVE_STATE_CALLED
```
private static final int PFLAG_SAVE_STATE_CALLED   = 0x00020000;
```
    这个应该是是否在子类实现 onSaveInstanceState() 的标志，通过下面的代码打印的异常可以推断
 ```
    if ((mPrivateFlags & PFLAG_SAVE_STATE_CALLED) == 0) {
                throw new IllegalStateException(
                        "Derived class did not call super.onSaveInstanceState()");
            }
```
继续看  Parcelable state = onSaveInstanceState();
```
    @CallSuper
    protected Parcelable onSaveInstanceState() {
        mPrivateFlags |= PFLAG_SAVE_STATE_CALLED;
        if (mStartActivityRequestWho != null) {
            BaseSavedState state = new BaseSavedState(AbsSavedState.EMPTY_STATE);
            state.mStartActivityRequestWhoSaved = mStartActivityRequestWho;
            return state;
        }
        return BaseSavedState.EMPTY_STATE;
    }
```
由上面代码可知，默认设置的标志位为空，若不为空，则返回相应的state
最后由 container 将相应的 View 的 ID 和 state 进行保存。代码如下
```
    if (state != null) {
                // Log.i("View", "Freezing #" + Integer.toHexString(mID)
                // + ": " + state);
                container.put(mID, state);
            }
```
至此，我们可以知道关于 onSaveInstanceState()，保存了 View 有用的数据，包括 ID 和 View 的各种状态到一个 Parcelable 对象并返回。在Activity 的 onSaveInstanceState(Bundle outState) 中通过 Window 的
 saveHierarchyState() 方法，最终调用 View 的 onSaveInstanceState ()，返回Parcelable对象，接着用 Bundle 的 putParcelable 方法保存在 Bundle  的实例 outState 中。key 值为WINDOW_HIERARCHY_TAG。
---
#### Activity 的状态还原
如果系统终止您的应用进程之后，用户返回您的 Activity，则系统会重建该 Activity，并将 Bundle 同时传给 onCreate() 和 onRestoreInstanceState()。可以使用上述任一方法从 Bundle 提取您保存的状态并恢复该 Activity 状态。如果没有状态信息需要恢复，则传递给您的 Bundle是为null（如果是首次创建该 Activity，就会出现这种情况）。

来看它的使用吧
```
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
         /**
         * 可以在次使用 Bundle 的一系列方法来获取信息
         * 比如:
         *     savedInstanceState.getInt();
         */
    }
```
继续看
```
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (mWindow != null) {
            Bundle windowState = savedInstanceState.getBundle(WINDOW_HIERARCHY_TAG);
            if (windowState != null) {
                mWindow.restoreHierarchyState(windowState);
            }
        }
    }
```
在 onSaveInstanceState() 中，Bundle 的 key 值为 WINDOW_HIERARCHY_TAG，在这里自然也使用它来获取数据。接着，如果数据不为空，则使用 restoreHierarchyState() 。继续跟踪
```
    /** {@inheritDoc} */
    @Override
    public void restoreHierarchyState(Bundle savedInstanceState) {
        if (mContentParent == null) {
            return;
        }
        SparseArray<Parcelable> savedStates
                = savedInstanceState.getSparseParcelableArray(VIEWS_TAG);
        if (savedStates != null) {
            mContentParent.restoreHierarchyState(savedStates);
        }
        // 省略部分代码
            }
        }
    }
```
由上面源码中可知
先通过 VIEWS_TAG 获取 View 的信息。再通过 View 的 restoreHierarchyState() 方法还原。继续跟踪
```
    public void restoreHierarchyState(SparseArray<Parcelable> container) {
        dispatchRestoreInstanceState(container);
    }
```
继续
```
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        if (mID != NO_ID) {
            Parcelable state = container.get(mID);
            if (state != null) {
                // Log.i("View", "Restoreing #" + Integer.toHexString(mID)
                // + ": " + state);
                mPrivateFlags &= ~PFLAG_SAVE_STATE_CALLED;
                onRestoreInstanceState(state);
                if ((mPrivateFlags & PFLAG_SAVE_STATE_CALLED) == 0) {
                    throw new IllegalStateException(
                            "Derived class did not call super.onRestoreInstanceState()");
                }
            }
        }
    }
```
哎，有种似曾相识的感觉，从上面可知，先是通过 mID 获取返回一个 Parcelable 对象实例 state，接着再使用 View 的 onRestoreInstanceState() 方法，继续看
```
    @CallSuper
    protected void onRestoreInstanceState(Parcelable state) {
        mPrivateFlags |= PFLAG_SAVE_STATE_CALLED;
        if (state != null && !(state instanceof AbsSavedState)) {
            throw new IllegalArgumentException("Wrong state class, expecting View State but "
                    + "received " + state.getClass().toString() + " instead. This usually happens "
                    + "when two views of different type have the same id in the same hierarchy. "
                    + "This view's id is " + ViewDebug.resolveId(mContext, getId()) + ". Make sure "
                    + "other views do not use the same id.");
        }
        if (state != null && state instanceof BaseSavedState) {
            mStartActivityRequestWho = ((BaseSavedState) state).mStartActivityRequestWhoSaved;
        }
    }
```
最终是在 View 的 onRestoreInstanceState() 方法中找到是在哪里保存的状态。至此，分析结束。
最后看一个整体图
![image.png](http://upload-images.jianshu.io/upload_images/10539135-22c7a88229ee76e0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



