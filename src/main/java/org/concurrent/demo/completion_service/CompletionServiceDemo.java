package org.concurrent.demo.completion_service;

import java.util.concurrent.*;

/**
 * java.util.concurrent.CompletionService
 * Future<V> submit(Callable<V> task);
 * Future<V> submit(Runnable task, V result);
 * Future<V> take() throws InterruptedException;
 * Future<V> poll();
 * Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException;
 */
public class CompletionServiceDemo {

    /**
     * 线程池大小
     */
    static final Integer POOL_SIZE = 5;

    /**
     * 创建一个固定容量的线程池
     */
    static final ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);

    /**
     * 指定executor创建
     */
    static CompletionService<String> completionService_1 = new ExecutorCompletionService(executor);

    /**
     * 指定executor和队列创建
     */
    static CompletionService<String> completionService_2 = new ExecutorCompletionService(executor, new LinkedBlockingDeque<>(5));

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //  submitCallable();
        //  submitRunnable();
        // takeNoElement();
        // takeElement();
        //   pollNoElement();
        //   pollElement();
        pollElementTimeout();
    }

    /**
     * Future<V> submit(Callable<V> task);
     */
    static void submitCallable() throws ExecutionException, InterruptedException {
        Future<String> submit = completionService_1.submit(() -> "success");
        String result = submit.get();
        System.out.println(result);
        executor.shutdown();
    }

    /**
     * Future<V> submitRunnable(Runnable task, V result);
     */
    static void submitRunnable() throws ExecutionException, InterruptedException {
        Future<String> submit1 = completionService_1.submit(() -> System.out.println("Runnable start"), "success");
        String result = submit1.get();
        System.out.println(result);
        executor.shutdown();
    }

    /**
     * 如果队列为空,take方法会阻塞
     * Future<V> take() throws InterruptedException;
     */
    static void takeNoElement() throws ExecutionException, InterruptedException {
        //此时队列为空,方法会阻塞,不会执行下去
        Future<String> take = completionService_1.take();
        System.out.println(take);
        executor.shutdown();
    }

    /**
     * 队列不为空
     * Future<V> take() throws InterruptedException;
     */
    static void takeElement() throws ExecutionException, InterruptedException {
        //此时队列为空,方法会阻塞,不会执行下去
        completionService_1.submit(() -> "success");
        Future<String> take = completionService_1.take();
        System.out.println(take.get());
        executor.shutdown();
    }

    /**
     * 如果队列为空,poll方法返回null
     * Future<V> poll() throws InterruptedException;
     */
    static void pollNoElement() throws ExecutionException, InterruptedException {
        //此时队列为空,方法会阻塞,不会执行下去
        Future<String> take = completionService_1.poll();
        System.out.println(take);
        executor.shutdown();
    }

    /**
     * 队列不为空
     * Future<V> poll() throws InterruptedException;
     */
    static void pollElement() throws ExecutionException, InterruptedException {
        //此时队列为空,方法会阻塞,不会执行下去
        completionService_1.submit(() -> "success");
        Future<String> take = completionService_1.poll();
        System.out.println(take.get());
        executor.shutdown();
    }

    /**
     * 超时设置,如果到了超时时间,任务还没有处理完成,直接返回null
     * Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException;
     */
    static void pollElementTimeout() throws ExecutionException, InterruptedException {
        //此时队列为空,方法会阻塞,不会执行下去
        completionService_1.submit(() -> {
            TimeUnit.SECONDS.sleep(2);
            return "success";
        });
        Future<String> take = completionService_1.poll(1, TimeUnit.SECONDS);
        System.out.println(take);//输出null
        executor.shutdown();
    }

}
