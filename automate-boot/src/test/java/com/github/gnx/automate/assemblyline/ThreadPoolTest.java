package com.github.gnx.automate.assemblyline;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 22:21
 */
public class ThreadPoolTest {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            0,
            10,
            60L,
            TimeUnit.SECONDS,
            new SynchronousQueue(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    private static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            int index = i;
            queue.add(new Runnable() {
                @Override
                public void run() {
                    System.out.println("我是:" + index + "\t" + Thread.currentThread().getName());
                }
            });
        }


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Runnable runnable = queue.take();

                        while (true) {
                            try {
                                threadPoolExecutor.execute(runnable);
                                break;
                            } catch (RejectedExecutionException e) {

                            }
                            System.out.println("提交任务被拒绝,休眠3秒");
                            Thread.sleep(3000);
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


        Thread.sleep(10000);


    }


}
