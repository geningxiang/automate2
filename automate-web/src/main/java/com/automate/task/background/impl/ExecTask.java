package com.automate.task.background.impl;

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
public class ExecTask implements ITask {
    protected ExecCommand execCommand = null;

    protected ExecTask() {

    }

    public ExecTask(ExecCommand execCommand) {
        this.execCommand = execCommand;
    }

    @Override
    public void invoke() throws IllegalAccessException {
        ExecCommand execCommand = getExecCommand();
        if(execCommand != null) {
            ExecHelper.exec(execCommand);
        }
    }

    public ExecCommand getExecCommand() throws IllegalAccessException {
        return execCommand;
    }

    public void setExecCommand(ExecCommand execCommand) {
        this.execCommand = execCommand;
    }
}
