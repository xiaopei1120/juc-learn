package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 两个任务,使用最快执行的任务结果进行处理,有返回值
 * public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other,Function<? super T, U> fn);
 * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn);
 * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn,Executor executor);
 */
public class ApplyToEither extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        applyToEither();
        //  applyToEitherAsync();
        // applyToEitherAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
     * 两个任务,使用最快执行的任务结果进行处理
     */
    public static void applyToEither() throws ExecutionException, InterruptedException {
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

        Integer integer = supplyAsync1.applyToEither(supplyAsync2, result -> {
            System.out.println(Thread.currentThread().getName());//ForkJoinPool.commonPool-worker-5 有问题
            System.out.println("result:" + result);// 输出1
            return result;
        }).get();
        System.out.println(integer);// 输出1
    }

    /**
     * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn);
     * 两个任务,使用最快执行的任务结果进行处理,使用公共线程池
     */
    public static void applyToEitherAsync() throws ExecutionException, InterruptedException {
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

        Integer integer = supplyAsync1.applyToEitherAsync(supplyAsync2, result -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("result:" + result);// 输出1
            return result;
        }).get();
        System.out.println(integer);// 输出1
    }

    /**
     * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn,Executor executor);
     * 两个任务,使用最快执行的任务结果进行处理, 使用自定义线程池
     */
    public static void applyToEitherAsyncWithExecutor() throws ExecutionException, InterruptedException {
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

        Integer integer = supplyAsync1.applyToEitherAsync(supplyAsync2, result -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("result:" + result);// 输出1
            return result;
        }, executor).get();
        System.out.println(integer);// 输出1
    }
}
