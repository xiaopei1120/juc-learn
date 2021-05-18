package org.concurrent.demo.completion_service;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 批量处理任务
 */
public class BatchTaskDemo {

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
        start(3);
    }

    /**
     * 任务执行并获取结果
     *
     * @param size 需要执行的任务个数
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void start(int size) throws InterruptedException, ExecutionException {
        //批量提交任务,并行执行
        for (int i = 0; i < size; i++) {
            completionService_1.submit(new Task());
        }

        //批量获取任务结果
        ArrayList<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Future<String> take = completionService_1.take();
            list.add(take.get());
        }

        System.out.println(list);//输出[pool-1-thread-2, pool-1-thread-1, pool-1-thread-3]
        executor.shutdown();
    }


    /**
     * 获取任务运行的线程名称
     */
    static class Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            return Thread.currentThread().getName();
        }
    }


}
