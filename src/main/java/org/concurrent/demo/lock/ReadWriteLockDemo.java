package org.concurrent.demo.lock;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ReadWriteLockDemo {
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    HashMap<String, Object> map = new HashMap<>();
    public Object read() throws InterruptedException {
        readLock.lock();
        try {
           // Thread.sleep(10000);
            System.out.println("read map is:" + map);
            return map;
        } finally {
            readLock.unlock();
        }
    }
    public void write(String key, Object o) {
        writeLock.lock();
        try {
            map.put(key, o);
            System.out.println("write map is:" + map);
        } finally {
            writeLock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockDemo lockDemo = new ReadWriteLockDemo();
        Thread readThread = new Thread(() -> {
            try {
                lockDemo.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        readThread.start();
     //   readThread.join();
        Thread writeThread = new Thread(() -> {
            lockDemo.write("key1", "value1");
        });
        writeThread.start();
    }
}
