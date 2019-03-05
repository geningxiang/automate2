package com.automate.task.background.assembly.impl;

import com.automate.common.SystemConfig;
import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.entity.SourceCodeEntity;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecHelper;
import com.automate.task.background.AbstractBackgroundTask;
import com.automate.task.background.assembly.AbstractAssemblyStepTask;
import com.automate.task.background.assembly.IAssemblyStepTask;

import java.io.File;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:15
 */
public abstract class AbstractExecAssemblyStepTask extends AbstractAssemblyStepTask {

    protected ExecCommand execCommand = null;


    protected abstract ExecCommand buildExecCommand() throws Exception;


    @Override
    public boolean doInvoke() throws Exception {
        ExecCommand execCommand = buildExecCommand();
        if (execCommand != null) {

            execCommand.setDir(new File(SystemConfig.getSourceCodeDir(getSourceCodeEntity())));

            ExecHelper.exec(execCommand);

            appendContent(execCommand.getOut().toString());

            return execCommand.getExitValue() == 0;

        } else {
            appendContent("execCommand is null");
            return true;
        }
    }


}
