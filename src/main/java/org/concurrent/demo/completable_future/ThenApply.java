package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 串行执行任务,具有依赖关系
 * public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
 * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
 * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
 */
public class ThenApply extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        //  thenApply();
        //thenApplyAsync();
        thenApplyAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
     * 将上一个任务的结果传递给下个任务,串行处理任务,在调用线程执行,不是异步线程处理的
     * A 完成了任务,A的结果给B,B才开始执行任务
     */
    public static void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = runAsync.thenApply((result) -> {
            printThreadName();//输出main
            return "A";
        });
        String finalResult = completableFuture.thenApply(result -> result.concat("B")).get();
        System.out.println("thenApply finalResult: " + finalResult);//输出AB
    }

    /**
     * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
     * 将上一个任务的结果传递给下个任务,串行处理任务,公共线程池执行任务
     * A 完成了任务,A的结果给B,B才开始执行任务
     */
    public static void thenApplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = runAsync.thenApplyAsync((result) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            return "A";
        });
        String finalResult = completableFuture.thenApply(result -> result.concat("B")).get();
        System.out.println("thenApplyAsync finalResult: " + finalResult);//输出AB
    }

    /**
     * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
     * 将上一个任务的结果传递给下个任务,串行处理任务,自定义线程池
     * A 完成了任务,A的结果给B,B才开始执行任务
     */
    public static void thenApplyAsyncWithExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = runAsync.thenApplyAsync((result) -> {
            printThreadName();//输出pool-1-thread-*
            return "A";
        }, executor);
        String finalResult = completableFuture.thenApply(result -> result.concat("B")).get();
        System.out.println("thenApplyAsyncWithExecutor finalResult: " + finalResult);//输出AB
    }
}
