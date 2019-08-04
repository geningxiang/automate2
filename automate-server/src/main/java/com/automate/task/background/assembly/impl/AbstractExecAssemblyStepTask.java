//package com.automate.task.background.assembly.impl;
//
//import com.automate.common.SystemConfig;
//import com.automate.exec.ExecCommand;
//import com.automate.exec.ExecHelper;
//import com.automate.task.background.assembly.AbstractAssemblyStepTask;
//
//import java.io.File;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @author: genx
// * @date: 2019/2/2 16:15
// */
//public abstract class AbstractExecAssemblyStepTask extends AbstractAssemblyStepTask {
//
//    protected ExecCommand execCommand = null;
//
//
//    protected abstract ExecCommand buildExecCommand() throws Exception;
//
//
//    @Override
//    public boolean doInvoke() throws Exception {
//        ExecCommand execCommand = buildExecCommand();
//        if (execCommand != null) {
//
//            execCommand.setDir(new File(SystemConfig.getSourceCodeDir(getProjectEntity())));
//
//
//            ExecHelper.exec(execCommand);
//
//            appendLine(execCommand.getOut().toString());
//
//            return execCommand.getExitValue() == 0;
//
//        } else {
//            appendLine("execCommand is null");
//            return true;
//        }
//    }
//
//
//}
