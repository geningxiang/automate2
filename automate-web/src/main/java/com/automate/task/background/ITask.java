package com.automate.task.background;

import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.entity.SourceCodeEntity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:16
 */
public interface ITask {

    void setSourceCodeId(int sourceCodeId);

    default String[] getLocks(){
        return null;
    }

    void invoke(SourceCodeEntity sourceCodeEntity) throws Exception;

    void setAssemblyLineTaskLogEntity(AssemblyLineTaskLogEntity assemblyLineTaskLogEntity);

    AssemblyLineTaskLogEntity getAssemblyLineTaskLogEntity();

}
