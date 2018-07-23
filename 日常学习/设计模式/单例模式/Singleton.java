package main;

/**
 * Created by Asus- on 2018/7/16.
 */
public class Singleton {

    private volatile static Singleton singleton = null;

    private Singleton() {
    }

    /**
     * 双重检查锁单例模式--支持多线程、可以确保只创建一个实例
     *
     * @return
     */
    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

//    /**
//     * 饿汉式单例模式
//     * 缺点：不适用于多线程
//     */
//    public static Singleton getInstance2(){
//        singleton = new Singleton();
//        return singleton;
//    }
//
//    /**
//     * 懒汉式单例模式--
//     * 缺点：每个线程来都要加同步锁，释放锁，消耗性能
//     */
//    public synchronized static Singleton getInstance3(){
//        if (singleton == null)
//            singleton = new Singleton();
//        return singleton;
//    }

//    /**
//     * 单重检查，锁单例模式
//     * 缺点：可能会创建多个实例
//     * @return
//     */
//    public static Singleton getInstance4() {
//        if (singleton == null) {
//            synchronized (Singleton.class) {
//                singleton = new Singleton();
//            }
//        }
//        return singleton;
//    }

}
