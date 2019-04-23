package com.automate.task.background;

import org.springframework.lang.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 14:54
 */
public class BackgroundLock {

    //private static final Map<String, Semaphore> LOCK_MAP = new HashMap(256);

    /**
     * TODO KEY 越来越多咋办呢
     */
    private static final ConcurrentHashMap<String, Semaphore> LOCK_MAP = new ConcurrentHashMap<>(256);

    /**
     * 操作 map 时的锁
     * 用 ConcurrentHashMap 的话  一直想不出来 如何在删除元素时 保持原子性
     */
    private static final Lock LOCK = new ReentrantLock();

    public static void acquire(AbstractBackgroundTask task) throws InterruptedException {
        if (task.getLocks() != null) {
            task.updateStatus(BackgroundStatus.acquire);
            for (int i = 0; i < task.getLocks().length; i++) {

                String key = task.getLocks()[i];
                Semaphore s = new Semaphore(1);
                Semaphore value = LOCK_MAP.putIfAbsent(key, s);
                if (value == null) {
                    value = s;
                }

                //获得许可
                value.acquire();
                task.setLockIndex(i + 1);

            }
        }
    }

    public static void release(AbstractBackgroundTask task) {
        if (task.getLocks() != null) {
            for (int i = task.getLocks().length - 1; i >= 0; i--) {
                LOCK.lock();
                try {
                    Semaphore s = LOCK_MAP.get(task.getLocks()[i]);
                    if (s != null) {
                        //释放资源
                        s.release();
                        LOCK_MAP.remove(task.getLocks()[i]);

                    }
                } finally {
                    LOCK.unlock();
                }

            }
        }
    }

    /**
     * 尝试获取指定的锁 并执行传入的Callable
     * 一般用于同步请求
     * @param lockKey
     * @param callable
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T tryLock(String lockKey, Callable<T> callable) throws Exception {
        Semaphore s = new Semaphore(1);
        Semaphore value = LOCK_MAP.putIfAbsent(lockKey, s);
        if (value == null) {
            value = s;
        }

        if(!value.tryAcquire()){
            throw new RuntimeException("["+lockKey + "]已被后台任务锁定,请稍后再试");
        }
        try{
            return callable.call();
        } finally {
            release(value);
        }
    }

    private static void release(@NonNull Semaphore value){
        try {
            LOCK.lock();
            value.release();
            LOCK_MAP.remove(value);
        } finally {
            LOCK.unlock();
        }
    }

}
