package com.automate.controller.admin;

import com.automate.task.background.AbstractBackgroundTask;
import com.automate.task.background.BackgroundLock;
import com.automate.task.background.BackgroundTaskManager;
import com.automate.task.background.IBackgroundMonitor;
import org.apache.commons.lang3.RandomUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/4 19:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class AdminBackgroundControllerTest {

    @Autowired
    private BackgroundTaskManager backgroundTaskManager;

    /**
     * 造模拟数据
     *
     * @param modelMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/mock")
    public String mock(ModelMap modelMap) {
        backgroundTaskManager.setBackgroundTaskMonitor(new IBackgroundMonitor() {
            @Override
            public void onWait(long uniqueId, String name, int waitingSize) {
                //System.out.println(String.format("【onwait】%s-%s, waiting:%d", uniqueId, name, waitingSize));
            }

            @Override
            public void onStart(long uniqueId, String name, int runningSize, int waitingSize) {
                //System.out.println(String.format("【onstart】%s-%s, running:%d, waiting:%d", uniqueId, name, runningSize, waitingSize));
            }

            @Override
            public void onComplete(long uniqueId, String name, String completeType, int runningSize, int waitingSize) {
                System.out.println(String.format("【oncomplete】%s-%s, result:%s, running:%d, waiting:%d", uniqueId, name, completeType, runningSize, waitingSize));
            }
        });
        for (int i = 0; i < 2000; i++) {
            final int index = i;
            backgroundTaskManager.execute(new AbstractBackgroundTask(bulidLocks()) {
                @Override
                public String getName() {
                    return "I am " + index;
                }

                @Override
                public void run() {
                    try {
                        Thread.sleep(RandomUtils.nextInt(2000, 5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return "success";
    }

    private BackgroundLock.Builder bulidLocks() {
        String[] ss = new String[]{"lock1", "lock2", "lock3", "lock4", "lock5", "lock6", "lock7", "lock8", "lock9", "lock10"};

        Set<String> locks = new HashSet<>();

        for (int i = 0, total = RandomUtils.nextInt(0, 5); i < total; i++) {
            locks.add(ss[RandomUtils.nextInt(0, ss.length)]);
        }


        return null;
    }

}