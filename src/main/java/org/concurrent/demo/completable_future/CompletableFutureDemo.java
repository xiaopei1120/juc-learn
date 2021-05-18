package org.concurrent.demo.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {

    /**
     * 线程池大小
     */
    static final Integer POOL_SIZE = 5;

    /**
     * 创建一个固定容量的线程池
     */
    static final java.util.concurrent.ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);

    /**
     * 只为演示一个任务执行时做的操作,无其他含义(就是指平时开发时候我们自己写的业务逻辑代码)
     */
    static final String taskDemo = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>[%s] task start<<<<<<<<<<<<<<<<<<<<<<<<<";

    static final CompletableFuture runAsync = CompletableFuture.runAsync(() -> System.out.print(""));

    static final CompletableFuture supplyAsync = CompletableFuture.supplyAsync(() -> "supplyAsync");

    static final CompletableFuture runAsyncThrowable = CompletableFuture.runAsync(() -> System.out.println(1 / 0));

    static final CompletableFuture supplyAsyncThrowable = CompletableFuture.supplyAsync(() -> {
        throw new RuntimeException("supplyAsync exception");
    });

    static String formatter(String print) {
        return String.format(taskDemo, print);
    }

    static void printThreadName() {
        System.err.println("当前运行的线程名称为:" + Thread.currentThread().getName());
    }

}
