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
    private final ExecCommand execCommand;

    public ExecTask(ExecCommand execCommand) {
        this.execCommand = execCommand;
    }

    @Override
    public void invoke() {
        ExecHelper.exec(execCommand);
    }
}
