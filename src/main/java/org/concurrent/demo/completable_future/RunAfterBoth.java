package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 两个任务,都执行完成才进行处理,无返回值
 * public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,Runnable action);
 * public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action);
 * public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action,Executor executor);
 */
public class RunAfterBoth extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        //    runAfterBoth();
        //       runAfterBothAsync();
        runAfterBothAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,Runnable action);
     * 两个任务,都执行完成才进行处理,无返回值
     */
    public static void runAfterBoth() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync1 finish");
            return 1;
        });

        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync2 finish");
            return 2;
        });

        supplyAsync1.runAfterBoth(supplyAsync2, () -> {
            printThreadName();//ForkJoinPool.commonPool-worker-5 有问题
            System.out.println("开始执行后续操作了...........");
        });
    }

    /**
     * public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action);
     * 两个任务,都执行完成才进行处理,无返回值,使用公共线程池
     */
    public static void runAfterBothAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync1 finish");
            return 1;
        });

        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync2 finish");
            return 2;
        });

        supplyAsync1.runAfterBothAsync(supplyAsync2, () -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("开始执行后续操作了...........");
        });
    }

    /**
     * public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action,Executor executor);
     * 两个任务,都执行完成才进行处理,无返回值, 使用自定义线程池
     */
    public static void runAfterBothAsyncWithExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync1 finish");
            return 1;
        });

        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync2 finish");
            return 2;
        });

        supplyAsync1.runAfterBothAsync(supplyAsync2, () -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("开始执行后续操作了...........");
        }, executor);
    }
}
