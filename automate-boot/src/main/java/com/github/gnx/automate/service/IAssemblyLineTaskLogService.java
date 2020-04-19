package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.AssemblyLineTaskLogEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/2 22:02
 */
public interface IAssemblyLineTaskLogService {
    /**
     * 保存流水线单步执行日志
     * @param assemblyLineTaskLogEntity
     * @return
     */
    AssemblyLineTaskLogEntity save(AssemblyLineTaskLogEntity assemblyLineTaskLogEntity);

    /**
     * 根据流水线logId 查询 单步任务日志列表
     * @param assemblyLineLogId
     * @return
     */
    List<AssemblyLineTaskLogEntity> findAllByAssemblyLineLogIdOrderById(int assemblyLineLogId);
}
