package com.automate.task.background;

import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.ApplicationUpdateApplyEntity;
import com.automate.service.ApplicationUpdateApplyService;
import com.automate.service.ApplicationUpdateLogService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/13 0:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class BackgroundLockTest {

    @Autowired
    private BackgroundTaskManager backgroundTaskManager;

    @Test
    public void test() throws Exception {

        backgroundTaskManager.setBackgroundTaskMonitor(new IBackgroundMonitor() {
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
        for (int i = 0; i < 100; i++) {
            backgroundTaskManager.execute(new BackgroundTestTask(i % 3));
        }


        Thread.sleep(600000);
    }

    public class BackgroundTestTask extends AbstractBackgroundTask {


        public BackgroundTestTask(int projectId) throws Exception {
            super(new BackgroundLock.Builder().addLockByProject(projectId));

        }

        @Override
        public String getName() {
            return "后台测试任务";
        }

        @Override
        public void run() {

            try {
                Thread.sleep(RandomUtils.nextLong(1000, 3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}