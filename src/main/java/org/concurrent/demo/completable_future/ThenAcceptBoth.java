package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 获取两个任务的结果,进行处理,不能获取处理后的结果
 * public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
 * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
 * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action,     Executor executor);
 */
public class ThenAcceptBoth extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
          thenAcceptBoth();
        //    thenAcceptBothAsync();
        //thenAcceptBothAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
     * 获取两个任务的结果,进行处理,同步处理
     */
    public static void thenAcceptBoth() throws ExecutionException, InterruptedException {
        supplyAsync.thenAcceptBoth(supplyAsync, (task1Result, task2Result) -> {
            printThreadName();//输出main
            //task1Result1 第一个任务的结果
            System.out.println("task1Result: " + task1Result);//输出supplyAsync
            //task1Result2 第二个任务的结果
            System.out.println("task2Result: " + task2Result);//输出supplyAsync
            //目前两个任务都是supplyAsync,所以输出结果是supplyAsync supplyAsync
        });
    }

    /**
     * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
     * 获取两个任务的结果,进行处理,使用公共线程池
     */
    public static void thenAcceptBothAsync() throws ExecutionException, InterruptedException {
        supplyAsync.thenAcceptBothAsync(supplyAsync, (task1Result, task2Result) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-3
            //task1Result1 第一个任务的结果
            System.out.println("task1Result: " + task1Result);//输出supplyAsync
            //task1Result2 第二个任务的结果
            System.out.println("task2Result: " + task2Result);//输出supplyAsync
            //目前两个任务都是supplyAsync,所以输出结果是supplyAsync supplyAsync
        });
    }

    /**
     * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action,     Executor executor);
     * 获取两个任务的结果,进行处理, 使用自定义线程池
     */
    public static void thenAcceptBothAsyncWithExecutor() throws ExecutionException, InterruptedException {
        supplyAsync.thenAcceptBothAsync(supplyAsync, (task1Result, task2Result) -> {
            printThreadName();//输出pool-1-thread-*
            //task1Result1 第一个任务的结果
            System.out.println("task1Result: " + task1Result);//输出supplyAsync
            //task1Result2 第二个任务的结果
            System.out.println("task2Result: " + task2Result);//输出supplyAsync
            //目前两个任务都是supplyAsync,所以输出结果是supplyAsync supplyAsync
        }, executor);
    }
}
