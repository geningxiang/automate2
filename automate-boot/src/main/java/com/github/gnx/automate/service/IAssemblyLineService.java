package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.field.req.AssemblyLineSaveField;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 21:23
 */
public interface IAssemblyLineService {

    Optional<AssemblyLineEntity> findById(int assemblyLineId);

    /**
     * 获取指定项目的所有流水线列表
     * @param projectId 项目ID
     * @return List<AssemblyLineEntity>
     */
    List<AssemblyLineEntity> getAllByProjectIdOrderById(int projectId);

    /**
     * 创建流水线
     * @param assemblyLineSaveField 流水线创建类
     * @param projectId 项目ID
     * @param userId 用户ID
     * @return AssemblyLineEntity
     */
    AssemblyLineEntity create(AssemblyLineSaveField assemblyLineSaveField, int projectId, int userId);

    AssemblyLineEntity update(int assemblyLineId, AssemblyLineSaveField assemblyLineSaveField);
}
