package com.automate.task.background;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

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
    private static final Object lock = new Object();

    public static void acquire(AbstractBackgroundAssembly task) throws InterruptedException {
        if (task.getLocks() != null) {
            task.updateStatus(BackgroundStatus.acquire);
            for (int i = 0; i < task.getLocks().length; i++) {

                String key = task.getLocks()[i];
//                synchronized (lock) {
                Semaphore s = new Semaphore(1);
                Semaphore value = LOCK_MAP.putIfAbsent(key, s);
                if (value == null) {
                    value = s;
                }
//                    s = LOCK_MAP.get(key);
//                    if (s == null) {
//                        s = new Semaphore(1);
//                        LOCK_MAP.put(key, s);
//                    }
////                }

                //获得许可
                value.acquire();
                task.setLockIndex(i + 1);

            }
        }
    }

    public static void release(AbstractBackgroundAssembly task) {
        if (task.getLocks() != null) {
            for (int i = task.getLocks().length - 1; i >= 0; i--) {
                synchronized (lock) {
                    Semaphore s = LOCK_MAP.get(task.getLocks()[i]);
                    if (s != null) {
                        //释放资源
                        s.release();
//                        LOCK_MAP.remove(task.getLocks()[i]);

                    }
                }

            }
        }
    }

}