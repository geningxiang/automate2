package com.automate.task.background;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 9:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class BackgroundAssemblyManagerTest {

    @Autowired
    BackgroundAssemblyManager backgroundAssemblyManager;

    @Test
    public void test() {
        backgroundAssemblyManager.setBackgroundTaskMonitor(new IBackgroundMonitor() {
            @Override
            public void onWait(long uniqueId, String name, int waitingSize) {
                System.out.println(String.format("【onwait】%s-%s, waiting:%d", uniqueId, name, waitingSize));
            }

            @Override
            public void onStart(long uniqueId, String name, int runningSize, int waitingSize) {
                System.out.println(String.format("【onstart】%s-%s, running:%d, waiting:%d", uniqueId, name, runningSize, waitingSize));
            }

            @Override
            public void onComplete(long uniqueId, String name, String completeType, int runningSize, int waitingSize) {
                System.out.println(String.format("【oncomplete】%s-%s, result:%s, running:%d, waiting:%d", uniqueId, name, completeType, runningSize, waitingSize));
            }
        });
        for (int i = 0; i < 500; i++) {
            final int index = i;
            backgroundAssemblyManager.execute(new AbstractBackgroundAssembly(null) {
                @Override
                public String getName() {
                    return "I am " + index;
                }

                @Override
                public void run() {
                    if (RandomUtils.nextInt(0, 100) < 10) {
                        throw new RuntimeException("报错啦");
                    }
                    try {
                        Thread.sleep(RandomUtils.nextInt(2000, 5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}