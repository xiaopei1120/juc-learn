package org.concurrent.demo.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * // 获取锁
 * void lock()
 * // 如果当前线程未被中断，则获取锁，可以响应中断
 * void lockInterruptibly()
 * // 返回绑定到此 Lock 实例的新 Condition 实例
 * Condition newCondition()
 * // 仅在调用时锁为空闲状态才获取该锁，可以响应中断
 * boolean tryLock()
 * // 如果锁在给定的等待时间内空闲，并且当前线程未被中断，则获取锁
 * boolean tryLock(long time, TimeUnit unit)
 * // 释放锁
 * void unlock()
 */
public class LockDemo {

    static Lock lock = new ReentrantLock();

    static int i = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> tryLock()).start();
        }
    }

    /**
     * void lock()//获取锁
     * void unlock()// 释放锁
     */
    static void lock() {
        lock.lock();
        try {
            System.out.println("lock start..................." + Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }

    static void tryLock() {
        if (lock.tryLock()) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("tryLock start..................." + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("获取锁失败");
        }

    }
}
