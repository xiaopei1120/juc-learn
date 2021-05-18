package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 任意任务执行完成都会进行下一步处理
 * public CompletionStage<Void> runAfterEither(CompletionStage<?> other,Runnable action);
 * public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action);
 * public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action,Executor executor);
 */
public class RunAfterEither extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        //    runAfterEither();
        //runAfterEitherAsync();
        runAfterEitherAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public CompletionStage<Void> runAfterEither(CompletionStage<?> other,Runnable action);
     * 任意任务执行完成都会进行下一步处理
     */
    public static void runAfterEither() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });

        supplyAsync1.runAfterEither(supplyAsync2, () -> {
            printThreadName();//ForkJoinPool.commonPool-worker-5 有问题
            System.out.println("开始执行后续操作了...........");
        });
    }

    /**
     * public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action);
     * 任意任务执行完成都会进行下一步处理,使用公共线程池
     */
    public static void runAfterEitherAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });

        supplyAsync1.runAfterEitherAsync(supplyAsync2, () -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("开始执行后续操作了...........");
        });
    }

    /**
     * public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action,Executor executor);
     * 任意任务执行完成都会进行下一步处理, 使用自定义线程池
     */
    public static void runAfterEitherAsyncWithExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });

        supplyAsync1.runAfterEitherAsync(supplyAsync2, () -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("开始执行后续操作了...........");
        }, executor);
    }
}
