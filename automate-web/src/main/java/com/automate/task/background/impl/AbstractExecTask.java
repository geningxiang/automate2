package com.automate.task.background.impl;

import com.automate.common.SystemConfig;
import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.entity.SourceCodeEntity;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecHelper;
import com.automate.exec.IExecStreamMonitor;
import com.automate.task.background.ITask;

import javax.activation.FileDataSource;
import java.io.File;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:15
 */
public abstract class AbstractExecTask implements ITask {
    protected ExecCommand execCommand = null;

    private AssemblyLineTaskLogEntity assemblyLineTaskLogEntity;

    protected abstract ExecCommand buildExecCommand() throws Exception;

    @Override
    public void setAssemblyLineTaskLogEntity(AssemblyLineTaskLogEntity assemblyLineTaskLogEntity){
        this.assemblyLineTaskLogEntity = assemblyLineTaskLogEntity;
    }

    @Override
    public AssemblyLineTaskLogEntity getAssemblyLineTaskLogEntity(){
        return this.assemblyLineTaskLogEntity;
    }



    @Override
    public void invoke(SourceCodeEntity sourceCodeEntity) throws Exception {
        if(assemblyLineTaskLogEntity != null){
            assemblyLineTaskLogEntity.setStartTime(new Timestamp(System.currentTimeMillis()));

        }
        ExecCommand execCommand = buildExecCommand();
        if (execCommand != null) {

            execCommand.setDir(new File(SystemConfig.getSourceCodeDir(sourceCodeEntity)));

            ExecHelper.exec(execCommand);

            if(assemblyLineTaskLogEntity != null){
                assemblyLineTaskLogEntity.setStatus(execCommand.getExitValue());
                assemblyLineTaskLogEntity.setContent(execCommand.getOut().toString());
            }
        }
        if(assemblyLineTaskLogEntity != null){
            assemblyLineTaskLogEntity.setEndTime(new Timestamp(System.currentTimeMillis()));
        }

    }


}
