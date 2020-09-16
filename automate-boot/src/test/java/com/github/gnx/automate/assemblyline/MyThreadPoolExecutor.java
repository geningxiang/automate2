package com.github.gnx.automate.assemblyline;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 22:48
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    private Semaphore semp;

    public MyThreadPoolExecutor(
                        int maximumPoolSize,
                                long keepAliveTime,
                                TimeUnit unit,
                                BlockingQueue<Runnable> workQueue,
                                ThreadFactory threadFactory,
                                RejectedExecutionHandler handler) {
        super(0, Integer.MAX_VALUE, keepAliveTime, unit, workQueue, threadFactory, handler);
        semp = new Semaphore(maximumPoolSize);

    }

    protected void beforeExecute(Thread t, Runnable r) {
        try {
            semp.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    protected void afterExecute(Runnable r, Throwable t) {
        semp.release();
    }


    public static void main(String[] args) {
        MyThreadPoolExecutor myThreadPoolExecutor = new MyThreadPoolExecutor(
               5,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );



        for (int i = 0; i < 1000; i++) {
            int index = i;
            myThreadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("我是:" + index + "\t" + Thread.currentThread().getName());

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
