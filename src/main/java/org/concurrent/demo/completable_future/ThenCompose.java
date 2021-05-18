package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 将上一个任务的结果传递给下个任务
 * public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn);
 * public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) ;
 * public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) ;
 */
public class ThenCompose extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        // thenCompose();
        //  thenComposeAsync();
        thenComposeAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn);
     * 将上一个任务的结果传递给下个任务
     */
    public static void thenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "A";
        }).thenCompose(result -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            return CompletableFuture.supplyAsync(() -> result + "B");
        });
        System.out.println("result: " + supplyAsync.get());//输出AB
    }

    /**
     * public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) ;
     * 将上一个任务的结果传递给下个任务,公共线程池
     */
    public static void thenComposeAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "A";
        }).thenComposeAsync(result -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            return CompletableFuture.supplyAsync(() -> result + "B");
        });
        System.out.println("result: " + supplyAsync.get());//输出AB
    }

    /**
     * public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) ;
     * 将上一个任务的结果传递给下个任务,自定义线程池
     */
    public static void thenComposeAsyncWithExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "A";
        }).thenComposeAsync(result -> {
            printThreadName();//输出pool-1-thread-*
            return CompletableFuture.supplyAsync(() -> result + "B");
        }, executor);
        System.out.println("result: " + supplyAsync.get());//输出AB
    }
}
