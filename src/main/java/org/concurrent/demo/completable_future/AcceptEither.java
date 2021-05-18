package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 两个任务,使用最快执行的任务结果进行处理,无返回值
 * public CompletionStage<Void> acceptEither(CompletionStage<? extends T> other,Consumer<? super T> action);
 * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action);
 * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action,Executor executor);
 */
public class AcceptEither extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        // acceptEither();
        //  acceptEitherAsync();
        acceptEitherAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public CompletionStage<Void> acceptEither(CompletionStage<? extends T> other,Consumer<? super T> action);
     * 两个任务,使用最快执行的任务结果进行处理
     */
    public static void acceptEither() throws ExecutionException, InterruptedException {
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

        supplyAsync1.acceptEither(supplyAsync2, result -> {
            printThreadName();//ForkJoinPool.commonPool-worker-5 有问题
            System.out.println("result:" + result);// 输出1
        });
    }

    /**
     * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action);
     * 两个任务,使用最快执行的任务结果进行处理,使用公共线程池
     */
    public static void acceptEitherAsync() throws ExecutionException, InterruptedException {
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

        supplyAsync1.acceptEitherAsync(supplyAsync2, result -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("result:" + result);// 输出1
        });
    }

    /**
     * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action,Executor executor);
     * 两个任务,使用最快执行的任务结果进行处理, 使用自定义线程池
     */
    public static void acceptEitherAsyncWithExecutor() throws ExecutionException, InterruptedException {
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

        supplyAsync1.acceptEitherAsync(supplyAsync2, result -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("result:" + result);// 输出1
        }, executor);
    }
}
