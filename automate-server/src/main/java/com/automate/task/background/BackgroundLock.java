package com.automate.task.background;

import org.springframework.lang.NonNull;

import java.util.TreeSet;
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
        if (task.getLockBuilder() != null) {
            TreeSet<String> locks = task.getLockBuilder().build();
            if(locks != null && locks.size() > 0){
                task.updateStatus(BackgroundStatus.acquire);
                for (String lockKey : locks) {
                    Semaphore s = new Semaphore(1);
                    Semaphore value = LOCK_MAP.putIfAbsent(lockKey, s);
                    if (value == null) {
                        value = s;
                    }
                    //获得许可
                    value.acquire();
                }
            }
        }
    }

    public static void release(AbstractBackgroundTask task) {
        if (task.getLockBuilder() != null) {
            TreeSet<String> locks = task.getLockBuilder().build();
            if(locks != null && locks.size() > 0){
                for (String lockKey : locks) {
                    LOCK.lock();
                    try {
                        Semaphore s = LOCK_MAP.get(lockKey);
                        if (s != null) {
                            //释放资源
                            s.release();
                            LOCK_MAP.remove(lockKey);
                        }
                    } finally {
                        LOCK.unlock();
                    }
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

    public static class Builder{
        private TreeSet<String> locks = new TreeSet();

        public Builder addLockByProject(int projectId){
            locks.add("Project_" + projectId);
            return this;
        }

        public Builder addLockByApplication(int applicationId){
            locks.add("ApplicationId" + applicationId);
            return this;
        }

        public TreeSet build(){
            return locks;
        }
    }

}
