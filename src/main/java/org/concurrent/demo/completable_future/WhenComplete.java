package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 当CompletableFuture的计算结果完成，或者抛出异常的时候，可以执行特定的行为
 * public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action)
 * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
 * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
 * whenComplete 和 whenCompleteAsync 方法
 */
public class WhenComplete extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
         whenComplete();
        // whenCompleteAsync();
        // whenCompleteAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action)
     * 当CompletableFuture的计算结果完成,在启动的线程执照该操作,非异步,例如,在main线程启动该任务,则执行操作的线程是main线程,不是异步线程
     */
    public static void whenComplete() {
        runAsync.whenComplete((result, throwable) -> {
            printThreadName();//输出main
            System.out.println("runAsync result :" + result);//输出null
            System.out.println("runAsync throwable :" + throwable);//输出null
        });

       /* runAsyncThrowable.whenComplete((result, throwable) -> {
            printThreadName();//输出main
            System.out.println("runAsyncThrowable result :" + result);//输出null
            System.out.println("runAsyncThrowable throwable :" + throwable);//输出throwable :java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        });

        supplyAsync.whenComplete((result, throwable) -> {
            printThreadName();//输出main
            System.out.println("supplyAsync result :" + result);//输出supplyAsync
            System.out.println("supplyAsync throwable :" + throwable);//输出null
        });

        supplyAsyncThrowable.whenComplete((result, throwable) -> {
            printThreadName();//输出main
            System.out.println("supplyAsync Throwable result :" + result);//输出null
            System.out.println("supplyAsync Throwable throwable :" + throwable);//输出throwable :java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        });*/
    }

    /**
     * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
     * 当CompletableFuture的计算结果完成,公共线程池执行该操作,可以看到输出线程名称为ForkJoinPool.commonPool-worker-* *代表第几个线程,例如ForkJoinPool.commonPool-worker-5
     */
    public static void whenCompleteAsync() {
        runAsync.whenCompleteAsync((result, throwable) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("runAsync result :" + result);//输出null
            System.out.println("runAsync throwable :" + throwable);//输出null
        });

        runAsyncThrowable.whenCompleteAsync((result, throwable) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("runAsyncThrowable result :" + result);//输出null
            System.out.println("runAsyncThrowable throwable :" + throwable);//输出throwable :java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        });

        supplyAsync.whenCompleteAsync((result, throwable) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("supplyAsync result :" + result);//输出supplyAsync
            System.out.println("supplyAsync throwable :" + throwable);//输出null
        });

        supplyAsyncThrowable.whenCompleteAsync((result, throwable) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("supplyAsync Throwable result :" + result);//输出null
            System.out.println("supplyAsync Throwable throwable :" + throwable);//输出throwable :java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        });
    }

    /**
     * public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
     * 当CompletableFuture的计算结果完成,在自定义线程池执行该操作,可以看到输出线程名称为pool-1-thread-* *代表第几个线程,例如pool-1-thread-1
     */
    public static void whenCompleteAsyncWithExecutor() {
        runAsync.whenCompleteAsync((result, throwable) -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("runAsync result :" + result);//输出null
            System.out.println("runAsync throwable :" + throwable);//输出null
        }, executor);

        runAsyncThrowable.whenCompleteAsync((result, throwable) -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("runAsyncThrowable result :" + result);//输出null
            System.out.println("runAsyncThrowable throwable :" + throwable);//输出throwable :java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        }, executor);

        supplyAsync.whenCompleteAsync((result, throwable) -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("supplyAsync result :" + result);//输出supplyAsync
            System.out.println("supplyAsync throwable :" + throwable);//输出null
        }, executor);

        supplyAsyncThrowable.whenCompleteAsync((result, throwable) -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("supplyAsync Throwable result :" + result);//输出null
            System.out.println("supplyAsync Throwable throwable :" + throwable);//输出throwable :java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        }, executor);
    }
}
