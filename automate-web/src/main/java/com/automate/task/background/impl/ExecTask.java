package com.automate.task.background.impl;

import com.automate.entity.BuildLogEntity;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecHelper;
import com.automate.task.background.ITask;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:15
 */
public class ExecTask implements ITask {
    protected ExecCommand execCommand = null;

    private long startTime;

    protected ExecTask() {

    }

    public ExecTask(ExecCommand execCommand) {
        this.execCommand = execCommand;
    }

    private void beforeInvoke() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void invoke() throws IllegalAccessException {
        beforeInvoke();

        ExecCommand execCommand = getExecCommand();
        if (execCommand != null) {
            ExecHelper.exec(execCommand);
        }

    }

    private void afterInvoke(ExecCommand execCommand) {
        BuildLogEntity buildLogEntity = new BuildLogEntity();
        buildLogEntity.setSourceCodeId(1);
        buildLogEntity.setBranchName("");
        buildLogEntity.setCommitId("");
        buildLogEntity.setCommitTimestamp(0L);


        buildLogEntity.setRemark("name");
        buildLogEntity.setCommand("");
        buildLogEntity.setContent(execCommand.getOut().toString());
        buildLogEntity.setCreateTime(new Timestamp(startTime));

    }

    public ExecCommand getExecCommand() throws IllegalAccessException {
        return execCommand;
    }

    public void setExecCommand(ExecCommand execCommand) {
        this.execCommand = execCommand;
    }
}
