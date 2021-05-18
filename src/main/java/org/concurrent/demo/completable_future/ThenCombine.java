package org.concurrent.demo.completable_future;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 获取两个任务的结果,进行处理
 * public <U,V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
 * public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
 * public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn,Executor executor);
 */
public class ThenCombine extends CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
             thenCombine();
        //  thenCombineAsync();
       // thenCombineAsyncWithExecutor();
        //等待任务执行,否则主线程会立即退出,看不到效果
        System.in.read();
    }


    /**
     * public <U,V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
     * 获取两个任务的结果,进行处理,同步处理
     */
    public static void thenCombine() throws ExecutionException, InterruptedException {
        Object result = supplyAsync.thenCombine(supplyAsync, (task1Result, task2Result) -> {
            printThreadName();//输出main
            //task1Result1 第一个任务的结果
            //task1Result2 第二个任务的结果
            //目前两个任务都是supplyAsync,所以输出结果是supplyAsync supplyAsync
            return task1Result.toString().concat(" " + task2Result.toString());
        }).get();
        System.out.println(result);//输出supplyAsync supplyAsync
    }

    /**
     * public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
     * 获取两个任务的结果,进行处理,使用公共线程池
     */
    public static void thenCombineAsync() throws ExecutionException, InterruptedException {
        Object result = supplyAsync.thenCombineAsync(supplyAsync, (task1Result, task2Result) -> {
            printThreadName();//输出ForkJoinPool.commonPool-worker-*
            //task1Result1 第一个任务的结果
            //task1Result2 第二个任务的结果
            //目前两个任务都是supplyAsync,所以输出结果是supplyAsync supplyAsync
            return task1Result.toString().concat(" " + task2Result.toString());
        }).get();
        System.out.println(result);//输出supplyAsync supplyAsync
    }

    /**
     * public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn,Executor executor);
     * 获取两个任务的结果,进行处理, 使用自定义线程池
     */
    public static void thenCombineAsyncWithExecutor() throws ExecutionException, InterruptedException {
        Object result = supplyAsync.thenCombineAsync(supplyAsync, (task1Result, task2Result) -> {
            printThreadName();//输出pool-1-thread-*
            //task1Result1 第一个任务的结果
            //task1Result2 第二个任务的结果
            //目前两个任务都是supplyAsync,所以输出结果是supplyAsync supplyAsync
            return task1Result.toString().concat(" " + task2Result.toString());
        }, executor).get();
        System.out.println(result);//输出supplyAsync supplyAsync
    }
}
