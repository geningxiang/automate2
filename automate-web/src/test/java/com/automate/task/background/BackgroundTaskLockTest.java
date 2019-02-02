package com.automate.task.background;

import com.automate.common.thread.GlobalThreadPoolManager;
import com.automate.test.concurrent.SemaphoreTest;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 15:10
 */
public class BackgroundTaskLockTest {

    private static final Object lock = new Object();

    public static void doRun() {
        synchronized (lock) {
            System.out.println("doRun");
        }
    }

    public static void doClear() {
        synchronized (lock) {
            System.out.println("doClear start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("doClear end");
        }
    }

    public static void main(String[] args) {
        GlobalThreadPoolManager globalThreadPoolManager = GlobalThreadPoolManager.getInstance();


        globalThreadPoolManager.execute(new Runnable() {
            @Override
            public void run() {
                BackgroundTaskLockTest.doClear();
            }
        });

        globalThreadPoolManager.execute(new Runnable() {
            @Override
            public void run() {
                BackgroundTaskLockTest.doRun();
            }
        });
    }

}