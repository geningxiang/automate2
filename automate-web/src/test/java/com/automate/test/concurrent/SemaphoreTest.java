package com.automate.test.concurrent;

import com.automate.common.thread.GlobalThreadPoolManager;
import com.automate.common.thread.GlobalThreadPoolManagerTest;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 13:34
 */
public class SemaphoreTest {
    private static final ConcurrentHashMap<String, Semaphore> map = new ConcurrentHashMap<String, Semaphore>();

    public static void main(String[] args) {

        GlobalThreadPoolManager globalThreadPoolManager = GlobalThreadPoolManager.getInstance();


        globalThreadPoolManager.execute(new Task("a", "b"));

        globalThreadPoolManager.execute(new Task("b", "a"));

    }


    private static class Task implements Runnable {
        private static final AtomicInteger index = new AtomicInteger(0);

        private final String[] keys;
        private final int id = index.incrementAndGet();

        private Task(String... keys) {
            //排序一遍，  防止不同的排序导致 互锁   !!很重要
            Arrays.sort(keys);
            this.keys = keys;
        }

        @Override
        public void run() {
            try {

                acquire(this.keys);

                System.out.println(id + " | " + StringUtils.join(this.keys, ","));
                Thread.sleep(RandomUtils.nextInt(1000, 5000));

                release(this.keys);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void acquire(String[] keys) throws InterruptedException {
            for (String key : keys) {
                Semaphore s = new Semaphore(1);
                Semaphore value = map.putIfAbsent(key, s);
                if (value == null) {
                    value = s;
                }
                //获得许可
                value.acquire();
                System.out.println(id + " 获得许可：" + key);
            }
        }

        public void release(String[] keys) throws InterruptedException {
            for (int i = keys.length - 1; i >= 0; i--) {

                Semaphore value = map.get(keys[i]);
                if (value != null) {
                    //释放资源
                    value.release();
                    System.out.println(id + " 释放许可：" + keys[i]);
                }
            }
        }
    }
}
