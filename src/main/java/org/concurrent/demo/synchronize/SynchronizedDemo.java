package org.concurrent.demo.synchronize;

/**
 *
 */
public class SynchronizedDemo {
    private static final int threadSize = 100;
    /**
     * 对当前对象加锁
     */
    public synchronized void synchronizedThis() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("synchronizedThis......");
    }
    /**
     * 对类加锁
     */
    public static synchronized void synchronizedClass() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("synchronizedClass........");
    }
    /**
     * 因为持有的是不同对象的锁,不会串行执行
     */
    static void testSynchronizedThis() {
        for (int i = 0; i < threadSize; i++) {
            SynchronizedDemo sync = new SynchronizedDemo();
            new Thread(() -> sync.synchronizedThis()).start();
        }
    }
    /**
     * 因为持有的是相同对象的锁,会串行执行
     */
    static void testSynchronizedThis_1() {
        SynchronizedDemo sync = new SynchronizedDemo();
        for (int i = 0; i < threadSize; i++) {
            new Thread(() -> sync.synchronizedThis()).start();
        }
    }
    /**
     * 整个程序会串行执行
     */
    static void testSynchronizedClass() {
        for (int i = 0; i < threadSize; i++) {
            new Thread(SynchronizedDemo::synchronizedClass).start();
        }
    }
    public static void main(String[] args) {
        //  testSynchronizedThis();
        testSynchronizedThis_1();
        // testSynchronizedClass();
    }
}
