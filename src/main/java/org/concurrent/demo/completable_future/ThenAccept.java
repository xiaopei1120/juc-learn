package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 处理任务的结果
 * public CompletionStage<Void> thenAccept(Consumer<? super T> action);
 * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
 * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor);
 */
public class ThenAccept extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        // thenAccept();
        //thenAcceptAsync();
        thenAcceptWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public CompletionStage<Void> thenAccept(Consumer<? super T> action);
     * 仅对任务结果的结果进行处理
     */
    public static void thenAccept() throws ExecutionException, InterruptedException {
        runAsync.thenAccept((result) -> {
            printThreadName();//输出main
            System.out.println("runAsync result:" + result);//输出null
            System.out.println("runAsync thenAccept process");
        });

        supplyAsync.thenAccept((result) -> {
            printThreadName();//输出main
            System.out.println("supplyAsync result:" + result);//输出supplyAsync
            System.out.println("supplyAsync thenAccept process");
        });
    }

    /**
     * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
     * 仅对任务结果的结果进行处理,使用公共线程池
     */
    public static void thenAcceptAsync() throws ExecutionException, InterruptedException {
        runAsync.thenAcceptAsync((result) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("runAsync result:" + result);//输出null
            System.out.println("runAsync thenAccept process");
        });

        supplyAsync.thenAcceptAsync((result) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("supplyAsync result:" + result);//输出supplyAsync
            System.out.println("supplyAsync thenAccept process");
        });
    }

    /**
     * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor);
     * 仅对任务结果的结果进行处理, 使用自定义线程池
     */
    public static void thenAcceptWithExecutor() throws ExecutionException, InterruptedException {
        runAsync.thenAcceptAsync((result) -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("runAsync result:" + result);//输出null
            System.out.println("runAsync thenAccept process");
        }, executor);

        supplyAsync.thenAcceptAsync((result) -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("supplyAsync result:" + result);//输出supplyAsync
            System.out.println("supplyAsync thenAccept process");
        }, executor);
    }
}
