package com.automate.common.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 全局的线程管理
 *
 * @author: genx
 * @date: 2019/2/1 21:42
 */
public class GobalThreadPoolManager {


    private class GobalThreadPool extends ThreadPoolExecutor {

        private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
        private final AtomicLong numTasks = new AtomicLong();
        private final AtomicLong totalTime = new AtomicLong();



        private GobalThreadPool() {
            super(0,
                    Integer.MAX_VALUE,
                    3L,
                    TimeUnit.SECONDS,
                    new SynchronousQueue<>(),
                    new CustomThreadFactory());
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            startTime.set(System.nanoTime());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            try {
                long endTime = System.nanoTime();
                long taskTime = endTime - startTime.get();
                numTasks.incrementAndGet();
                totalTime.addAndGet(taskTime);
            } finally {
                super.afterExecute(r, t);
            }
        }

        @Override
        protected void terminated() {
            try {
                System.out.println(String.format("Terminated: arg time = %d",
                        totalTime.get() / numTasks.get()));
            } finally {
                super.terminated();
            }
        }


    }

    private class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("CmdHelperThread-" + count.addAndGet(1));
            return t;
        }
    }
}
