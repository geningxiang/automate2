package com.automate.common.thread;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AtomicLongMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 全局的线程管理
 *
 * @author: genx
 * @date: 2019/2/1 21:42
 */
public class GlobalThreadPoolManager {
    private static final GlobalThreadPoolManager instance = new GlobalThreadPoolManager();

    public static final GlobalThreadPoolManager getInstance() {
        return instance;
    }

    private final GlobalThreadPool globalThreadPool = new GlobalThreadPool();

    private GlobalThreadPoolManager() {

    }

    public void execute(Runnable r) {
        globalThreadPool.execute(r);
    }

    public int size() {
        return globalThreadPool.runningSize.get();
    }

    /**
     * 查看运行信息
     *
     * @return
     */
    public Map<String, Long> runningInfo() {
        return globalThreadPool.runningMap.asMap();
    }

    private class GlobalThreadPool extends ThreadPoolExecutor {

        private final AtomicInteger runningSize = new AtomicInteger(0);


        //ConcurrentHashMap 在删除时 很难保证原子性
//        private final ConcurrentMap<String, AtomicInteger> runningMap = new ConcurrentHashMap(256);


        /**
         * runnable的running信息
         * 《类名, 正在运行的个数》
         * 来自于Google的Guava项目 线程安全，支持并发
         */
        private final AtomicLongMap<String> runningMap = AtomicLongMap.create();


        private GlobalThreadPool() {
            super(0, Integer.MAX_VALUE, 5L, TimeUnit.SECONDS, new SynchronousQueue<>(), new CustomThreadFactory());
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            runningSize.incrementAndGet();

            runningMap.incrementAndGet(r.getClass().getName());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            try {
                runningSize.decrementAndGet();

                long count = this.runningMap.decrementAndGet(r.getClass().getName());
                if(count == 0) {
                    this.runningMap.removeIfZero(r.getClass().getName());
                }

            } finally {
                super.afterExecute(r, t);
            }
        }

    }

    private class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("GlobalThread-" + count.addAndGet(1));
            return t;
        }
    }
}
