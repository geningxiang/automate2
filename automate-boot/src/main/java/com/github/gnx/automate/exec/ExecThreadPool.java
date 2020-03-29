package com.github.gnx.automate.exec;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/25 23:32
 */
public class ExecThreadPool {

    /**
     * 管理 用于 CMD 操作 的线程
     */
    private static ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3L, TimeUnit.SECONDS,
            new SynchronousQueue(), new ExecThreadPool.CustomThreadFactory());


    public static void execute(ExecStreamReader runnable){
        pool.execute(runnable);
    }

    public static <T> Future<T> submit(Callable<T>  task) throws Exception{
        return pool.submit(task);
    }

    private static class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("ExecStreamReadThread-" + count.addAndGet(1));
            return t;
        }
    }
}
