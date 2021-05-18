package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 可以对执行完的任务做结果和异常的处理
 * public <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn);
 * public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn);
 * public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn,Executor executor);
 */
public class Handle extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        //  handle();
        //   handleAsync();
        handleAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public <U> CompletableFuture<U> thenApply(Function<? super T,? extends U> fn)
     * 对任务结果和异常处理,如果有异常,处理之后还可以继续下一个任务,同步处理
     */
    public static void handle() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = runAsync.handle((result, t) -> {
            printThreadName();//输出main
            return "A";
        });
        String finalResult = completableFuture.get();
        System.out.println("handle finalResult: " + finalResult);//输出A

        CompletableFuture<String> completableFutureThrowable = runAsyncThrowable.handle((result, t) -> {
            printThreadName();//输出main
            if (t != null) {
                return "has Throwable";
            }
            return "A";
        });
        String finalResultThrowable = completableFutureThrowable.get();
        System.out.println("finalResultThrowable finalResult: " + finalResultThrowable);//输出has Throwable
    }

    /**
     * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn)
     * 对任务结果和异常处理,如果有异常,处理之后还可以继续下一个任务 使用公共线程池
     */
    public static void handleAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = runAsync.handleAsync((result, t) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            return "A";
        });
        String finalResult = completableFuture.get();
        System.out.println("handleAsync finalResult: " + finalResult);//输出A

        CompletableFuture<String> completableFutureThrowable = runAsyncThrowable.handleAsync((result, t) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            if (t != null) {
                return "has Throwable";
            }
            return "A";
        });
        String finalResultThrowable = completableFutureThrowable.get();
        System.out.println("finalResultThrowable finalResult: " + finalResultThrowable);//输出has Throwable
    }

    /**
     * public <U> CompletableFuture<U> thenApplyAsync(Function<? super T,? extends U> fn, Executor executor)
     * 对任务结果和异常处理,如果有异常,处理之后还可以继续下一个任务 使用自定义线程池
     */
    public static void handleAsyncWithExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = runAsync.handleAsync((result, t) -> {
            printThreadName();//输出pool-1-thread-*
            return "A";
        }, executor);
        String finalResult = completableFuture.get();
        System.out.println("handleAsyncWithExecutor finalResult: " + finalResult);//输出A

        CompletableFuture<String> completableFutureThrowable = runAsyncThrowable.handleAsync((result, t) -> {
            printThreadName();//输出pool-1-thread-*
            if (t != null) {
                return "has Throwable";
            }
            return "A";
        }, executor);
        String finalResultThrowable = completableFutureThrowable.get();
        System.out.println("finalResultThrowable finalResult: " + finalResultThrowable);//输出has Throwable
    }
}
