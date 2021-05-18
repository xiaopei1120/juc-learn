package org.concurrent.demo.semaphore;

import java.util.concurrent.Semaphore;

/**
 * void acquire() ：从信号量获取一个许可，如果无可用许可前将一直阻塞等待，
 * void acquire(int permits) ：获取指定数目的许可，如果无可用许可前也将会一直阻塞等待
 * boolean tryAcquire()：从信号量尝试获取一个许可，如果无可用许可，直接返回false，不会阻塞
 * boolean tryAcquire(int permits)： 尝试获取指定数目的许可，如果无可用许可直接返回false
 * boolean tryAcquire(int permits, long timeout, TimeUnit unit)： 在指定的时间内尝试从信号量中获取许可，如果在指定的时间内获取成功，返回true，否则返回false
 * void release()： 释放一个许可
 * int availablePermits()： 获取当前信号量可用的许可
 */
public class SemaphoreDemo {

    private static final int MAX_AVAILABLE = 3;
    private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

    public void start() {
        try {
            available.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int queueLength = available.getQueueLength();
        System.out.println("队列大小为: " + queueLength + "-" + Thread.currentThread().getName());
        try {
        } finally {
            available.release();
        }
    }

    public static void main(String[] args) {
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> semaphoreDemo.start()).start();
        }
    }

}
