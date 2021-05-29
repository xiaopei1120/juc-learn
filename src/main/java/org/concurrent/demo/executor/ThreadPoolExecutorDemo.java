package org.concurrent.demo.executor;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * public ThreadPoolExecutor(
 * int corePoolSize,//核心线程数
 * int maximumPoolSize,//最大线程数
 * long keepAliveTime,//线程存活时间
 * TimeUnit unit,存活时间单位
 * BlockingQueue<Runnable> workQueue,队列
 * ThreadFactory threadFactory,//创建线程的工程
 * RejectedExecutionHandler handler 饱和策略
 * }
 * <p>
 * 执行流程
 * 初始化线程之后,任务进来之后会先创建核心的线程数量,当超过核心线程数量之后,会将多余的任务放进队列里面
 * 此时要看队列的大小,比如队列容量为10,
 * 如果多余的任务<=10 则任务在队列等待,知道线程任务执行完成有空闲线程时会从队列拉取任务
 * 如果超过了队列的大小,就要和最大线程参数做关联
 * 如果最大线程大小<=核心线程,则不会创建线程,直接执行饱和策略
 * 如果最大线程大小>核心线程,则创建线程,拉取队列任务执行
 * 当队列任务都被执行完成了以后,超过核心线程数之外创建的线程会被回收,具体的回收时间要看keepAliveTime和unit参数
 * 例如keepAliveTime设置60,unit为SECONDS,则线程的存活时间为60秒,超过60秒之后就会被回收
 * 如果任务数超过了最大线程数也会触发饱和策略
 * CallerRunsPolicy
 * AbortPolicy
 * DiscardPolicy
 * DiscardOldestPolicy
 */
public class ThreadPoolExecutorDemo {

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>(10), new TestThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    /**
     * The default thread factory
     */
    static class TestThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        TestThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "test-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
    }
}
