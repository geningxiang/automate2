package com.automate.task.background.assembly.impl;

import com.automate.exec.ExecCommand;
import com.automate.exec.IExecStreamMonitor;
import com.automate.task.background.BackgroundTaskManager;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class BasicAssemblyTest {

    @Autowired
    BackgroundTaskManager backgroundTaskManager;

    @Test
    public void test() throws IllegalAccessException, InterruptedException {
        ExecCommand execCommand = new ExecCommand(ImmutableList.of("mvn clean package -DskipTests=true"), null, new File("D:/github-workspace/SpringBootDemo"), new IExecStreamMonitor() {
            @Override
            public void onStart(String commond) {
                System.out.println(commond);
            }

            @Override
            public void onMsg(String line) {
                System.out.println(line);
            }

            @Override
            public void onError(String line) {
                System.err.println(line);
            }

            @Override
            public void onEnd(int exitValue) {
                System.out.println("finished " + exitValue);
            }
        });

//        ExecAssemblyStepTask execTask = new ExecAssemblyStepTask(execCommand);
//
//        BasicAssembly basicAssembly = BasicAssembly.create("SpringBootDemo-编译", new IAssemblyStepTask[]{execTask});
//
//        backgroundAssemblyManager.execute(basicAssembly);
//
//        Thread.sleep(10000);
    }

}