package org.concurrent.demo.cyclic_barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/**
 * public CyclicBarrier(int parties, Runnable barrierAction) ;
 * public CyclicBarrier(int parties);
 * public int getParties();
 * public int await() throws InterruptedException, BrokenBarrierException ();
 * public int await(long timeout, TimeUnit unit)throws InterruptedException,   BrokenBarrierException, TimeoutException();
 * public boolean isBroken();
 * public void reset() ;
 * public int getNumberWaiting();
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.err.println(Thread.currentThread().getName() + "运行结束");
        });
        for (int j = 0; j < 5; j++) {
            new Task(cyclicBarrier, "任务" + j).start();
        }
    }
    static class Task extends Thread {
        private String name;
        private CyclicBarrier cyclicBarrier;
        public Task(CyclicBarrier cyclicBarrier, String name) {
            super(name);
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                //计数减1
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(name + "开始工作了>>>>>>>>>>>>>");
        }
    }
}
