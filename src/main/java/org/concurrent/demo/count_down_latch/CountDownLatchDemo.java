package org.concurrent.demo.count_down_latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * //构造一个计数器 count:计数器大小
 * public CountDownLatch(int count) ;
 * <p>
 * //等待计数器结果,如果不为0,则阻塞
 * public void await()throws InterruptedException();
 * <p>
 * //超时等待计数器结果,如果在设置时间内计数器扔不为0,则放弃等待,直接放行
 * public boolean await(long timeout,TimeUnit unit);
 * <p>
 * //计数器减1操作
 * public void countDown();
 * <p>
 * //获取计数器大小
 * public long getCount();
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        //计数器数量为2
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Task(countDownLatch, "A").start();
        new Task(countDownLatch, "B").start();
        //等到计数器为0,否则会一直阻塞
        //countDownLatch.await();
        //在设置的时间里等到计数器为0,如果超过设置时间就放行,不会阻塞
        countDownLatch.await(1, TimeUnit.SECONDS);
        System.out.println("任务执行完成........");
    }

    static class Task extends Thread {
        private CountDownLatch latch;

        public Task(CountDownLatch latch, String taskName) {
            super(taskName);
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": 开始执行.....");
            //计数器减1
            latch.countDown();
            //获取当前计数器的值
            long count = latch.getCount();
            System.out.println("当前计数器为:" + count);
        }
    }
}
