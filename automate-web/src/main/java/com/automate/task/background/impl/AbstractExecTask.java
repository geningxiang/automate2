package com.automate.task.background.impl;

import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecHelper;
import com.automate.task.background.ITask;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:15
 */
public abstract class AbstractExecTask implements ITask {
    protected ExecCommand execCommand = null;

    private long startTime;


    protected abstract ExecCommand buildExecCommand() throws Exception;

    private void beforeInvoke() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void invoke() throws Exception {
        beforeInvoke();

        ExecCommand execCommand = buildExecCommand();
        if (execCommand != null) {
            ExecHelper.exec(execCommand);
        }

    }

    private void afterInvoke(ExecCommand execCommand) {
        AssemblyLineTaskLogEntity model = new AssemblyLineTaskLogEntity();
    }

}
