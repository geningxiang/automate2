//package com.automate.task.background.assembly.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.automate.exec.ExecCommand;
//import com.automate.exec.ExecHelper;
//import com.automate.exec.IExecStreamMonitor;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @author: genx
// * @date: 2019/2/3 20:57
// */
//public class MavenTaskTest {
//
//    public static void main(String[] args) throws Exception {
//
//        IExecStreamMonitor execStreamMonitor = new IExecStreamMonitor() {
//            @Override
//            public void onStart(String commond) {
//                System.out.println(commond);
//            }
//
//            @Override
//            public void onMsg(String line) {
//                System.out.println(line);
//            }
//
//            @Override
//            public void onEnd(int exitValue) {
//                System.out.println("finished " + exitValue);
//            }
//        };
//
//        MavenAssemblyStepTask mavenTask = new MavenAssemblyStepTask();
//        mavenTask.setShortcut(MavenAssemblyStepTask.Lifecycle.compile.name());
//        mavenTask.setCustom("sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=00996135a7d5148b7fab335d4dba233c3dcb7afc");
//
//
//        String s = JSON.toJSONString(mavenTask);
//
//        MavenAssemblyStepTask task = JSON.parseObject(s, MavenAssemblyStepTask.class);
//
//        task.setExecStreamMonitor(execStreamMonitor);
//
//        ExecCommand execCommand = task.buildExecCommand();
//
//        ExecHelper.exec(execCommand);
//
//    }
//}