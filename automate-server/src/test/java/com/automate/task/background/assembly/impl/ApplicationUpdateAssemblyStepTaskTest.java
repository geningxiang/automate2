//package com.automate.task.background.assembly.impl;
//
//import com.automate.task.background.impl.ApplicationUpdateBackgroundTaskImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.HashMap;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @author: genx
// * @date: 2019/3/7 23:36
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:/spring/*.xml"})
//public class ApplicationUpdateAssemblyStepTaskTest {
//
//    @Test
//    public void doInvoke() {
//
//        ApplicationUpdateBackgroundTaskImpl task = new ApplicationUpdateBackgroundTaskImpl();
//
//        task.setPath("D:\\idea-workspace\\automate2\\automate-server\\target/Automate2.war");
//
//        task.setContainerId(1);
//
//        task.init(new HashMap<>(), 1, "master", "", 1, null,  null);
//
//        task.invoke();
//
//    }
//}