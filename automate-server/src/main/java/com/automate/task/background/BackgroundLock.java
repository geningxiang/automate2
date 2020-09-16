package com.automate.task.background;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 14:54
 */
public class BackgroundLock {

    private static Logger logger = LoggerFactory.getLogger(BackgroundLock.class);

    /**
     * TODO KEY 越来越多咋办呢
     */
    private static final Map<String, Semaphore> LOCK_MAP = new HashMap(256);

//    /**
//     * 操作 map 时的锁
//     * 用 ConcurrentHashMap 的话  一直想不出来 如何在删除元素时 保持原子性
//     */
//    private static final Lock LOCK = new ReentrantLock();

    public static void acquire(AbstractBackgroundTask task) throws InterruptedException {
        if (task.getLockBuilder() != null) {
            TreeSet<String> locks = task.getLockBuilder().build();
            if (locks != null && locks.size() > 0) {
                task.updateStatus(BackgroundStatus.acquire);
                for (String lockKey : locks) {
                    Semaphore value;
                    synchronized (BackgroundLock.class) {
                        value = LOCK_MAP.get(lockKey);
                        if (value == null) {
                            value = new Semaphore(1);
                            LOCK_MAP.put(lockKey, value);
                        }
                    }
                    logger.debug("申请锁:" + lockKey);
                    //获得许可
                    value.acquire();
                    logger.debug("获得锁:" + lockKey);
                }
            }
        }
    }

    public static void release(AbstractBackgroundTask task) {
        if (task.getLockBuilder() != null) {
            TreeSet<String> locks = task.getLockBuilder().build();
            if (locks != null && locks.size() > 0) {
                for (String lockKey : locks) {
                    synchronized (BackgroundLock.class) {
                        Semaphore s = LOCK_MAP.get(lockKey);
                        if (s != null) {
                            //释放资源
                            logger.debug("释放锁:" + lockKey);
                            s.release();
                        }
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
        Semaphore value;
        synchronized (BackgroundLock.class) {
            value = LOCK_MAP.get(lockKey);
            if (value == null) {
                value = new Semaphore(1);
            }
        }

        if (!value.tryAcquire()) {
            throw new RuntimeException("[" + lockKey + "]已被后台任务锁定,请稍后再试");
        }
        try {
            return callable.call();
        } finally {
            synchronized (BackgroundLock.class) {
                value.release();

            }
        }
    }


    public static class Builder {
        private TreeSet<String> locks = new TreeSet();

        public Builder addLockByProject(int projectId) {
            locks.add("Project_" + projectId);
            return this;
        }

        public Builder addLockByApplication(int applicationId) {
            locks.add("ApplicationId" + applicationId);
            return this;
        }

        public TreeSet build() {
            return locks;
        }
    }

}
