package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 不关心上一个任务的执行结果,只关心上个任务是否结束,一旦结束了,就执行任务
 * public CompletionStage<Void> thenRun(Runnable action);
 * public CompletionStage<Void> thenRunAsync(Runnable action);
 * public CompletionStage<Void> thenRunAsync(Runnable action,Executor executor);
 */
public class ThenRun extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        //thenRun();
        //  thenRunAsync();
        thenRunAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public CompletionStage<Void> thenRun(Runnable action);
     * 忽略上个任务执行结果,只做后续的处理,同步处理
     */
    public static void thenRun() throws ExecutionException, InterruptedException {
        runAsync.thenRun(() -> {
            printThreadName();//输出main
            System.out.println("runAsync thenRun process");
        });
    }

    /**
     * public CompletionStage<Void> thenRunAsync(Runnable action);
     * 忽略上个任务执行结果,只做后续的处理,使用公共线程池
     */
    public static void thenRunAsync() throws ExecutionException, InterruptedException {
        runAsync.thenRunAsync(() -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            System.out.println("runAsync thenRunAsync process");
        });
    }

    /**
     * public CompletionStage<Void> thenRunAsync(Runnable action,Executor executor);
     * 忽略上个任务执行结果,只做后续的处理, 使用自定义线程池
     */
    public static void thenRunAsyncWithExecutor() throws ExecutionException, InterruptedException {
        runAsync.thenRunAsync(() -> {
            printThreadName();//输出pool-1-thread-*
            System.out.println("runAsync thenRunAsyncWithExecutor process");
        }, executor);
    }
}
