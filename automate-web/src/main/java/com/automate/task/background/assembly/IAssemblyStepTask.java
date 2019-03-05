package com.automate.task.background.assembly;

import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.entity.SourceCodeEntity;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *  后台流水线中的单步任务
 * @author: genx
 * @date: 2019/2/2 16:16
 */
public interface IAssemblyStepTask {

    default String[] getLocks(){
        return null;
    }

    void setName(String name);

    void cancel(String reason);

    String getName();

    /**
     * 验证字段正确性
     */
    void valid() throws Exception;


    /**
     * 初始化
     * @param localCacheMap
     * @param sourceCodeId
     * @param branch
     * @param commitId
     * @param serverId
     * @param applicationId     暂时没有用  可以传空
     * @param assemblyLineLogId
     */
    void init(Map<String,Object> localCacheMap, Integer sourceCodeId, String branch, String commitId, Integer serverId, Integer applicationId, Integer assemblyLineLogId);



    boolean invoke();

}
