package com.automate.common.thread;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 11:26
 */
public class GlobalThreadPoolManagerTest {

    @Test
    public void execute() {
        GlobalThreadPoolManager globalThreadPoolManager = GlobalThreadPoolManager.getInstance();

        for (int i = 0; i < 500; i++) {
            globalThreadPoolManager.execute(new Runnable1());
        }

        for (int i = 0; i < 500; i++) {
            globalThreadPoolManager.execute(new Runnable2());
        }

        for (int i = 0; i < 500; i++) {
            globalThreadPoolManager.execute(new Runnable3());
        }

        for (int i = 0; i < 200; i++) {

            System.out.println(globalThreadPoolManager.runningInfo().entrySet());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private class Runnable1 implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(RandomUtils.nextInt(3000, 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class Runnable3 implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(RandomUtils.nextInt(3000, 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class Runnable2 implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(RandomUtils.nextInt(3000, 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}