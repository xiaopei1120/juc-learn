package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * runAsync 和 supplyAsync方法
 * public static CompletableFuture<Void> runAsync(Runnable runnable)
 * public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
 * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
 * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
 */
public class RunAsync_SupplyAsync extends CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
     //   runAsync();
        runAsyncWithExecutor();
    //    supplyAsync();
     //   supplyAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public static CompletableFuture<Void> runAsync(Runnable runnable)
     * 异步运行一个任务,无返回值,使用公共线程池
     */
    public static void runAsync() {
        CompletableFuture.runAsync(() -> System.out.println(formatter("runAsync")));
    }

    /**
     * public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
     * 异步运行一个任务,无返回值,自定义线程池
     */
    public static void runAsyncWithExecutor() {
        CompletableFuture.runAsync(() -> {
            printThreadName();
            System.out.println(formatter("runAsyncWithExecutor"));
        }, executor);
    }

    /**
     * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
     * 异步执行一个任务,可以获取到线程执行的结果,使用公共线程池
     */
    public static void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsyncCompletableFuture = CompletableFuture.supplyAsync(() -> "supplyAsync");
        String result = supplyAsyncCompletableFuture.get();
        System.out.println(formatter("supplyAsync"));
        System.out.println("supplyAsync result: " + result);
    }

    /**
     * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
     * 异步执行一个任务,可以获取到线程执行的结果,自定义线程池
     */
    public static void supplyAsyncWithExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsyncCompletableFuture = CompletableFuture.supplyAsync(() -> {
            printThreadName();
            System.out.println(formatter("supplyAsync"));
            return "supplyAsync";
        }, executor);
        String result = supplyAsyncCompletableFuture.get();
        System.out.println("supplyAsyncWithExecutor result: " + result);
    }


}
