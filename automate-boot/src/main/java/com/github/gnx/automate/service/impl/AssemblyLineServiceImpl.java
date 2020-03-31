package com.github.gnx.automate.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.field.req.AssemblyLineCreateField;
import com.github.gnx.automate.repository.AssemblyLineRepository;
import com.github.gnx.automate.service.IAssemblyLineService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 21:23
 */
@Service
public class AssemblyLineServiceImpl implements IAssemblyLineService {

    private final AssemblyLineRepository assemblyLineRepository;

    public AssemblyLineServiceImpl(AssemblyLineRepository assemblyLineRepository) {
        this.assemblyLineRepository = assemblyLineRepository;
    }

    @Override
    public Optional<AssemblyLineEntity> findById(int assemblyLineId){
        return this.assemblyLineRepository.findById(assemblyLineId);
    }

    @Override
    public List<AssemblyLineEntity> getAllByProjectIdOrderById(int projectId) {
        return this.assemblyLineRepository.getAllByProjectIdOrderById(projectId);
    }

    @Override
    public AssemblyLineEntity create(AssemblyLineCreateField assemblyLineCreateField, int projectId, int userId) {
        AssemblyLineEntity assemblyLineEntity = new AssemblyLineEntity();
        assemblyLineEntity.setProjectId(projectId);
        assemblyLineEntity.setBranches(assemblyLineCreateField.getBranches());
        assemblyLineEntity.setName(assemblyLineCreateField.getName());
        assemblyLineEntity.setRemark(assemblyLineCreateField.getRemark());
        assemblyLineEntity.setConfig(JSONArray.toJSONString(assemblyLineCreateField.getStepTasks()));
        assemblyLineEntity.setUserId(userId);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        assemblyLineEntity.setCreateTime(now);
        assemblyLineEntity.setUpdateTime(now);
        assemblyLineEntity.setLastRunTime(null);
        assemblyLineEntity.setAutoTrigger(assemblyLineCreateField.getAutoTrigger());
        assemblyLineEntity.setTriggerCron(assemblyLineCreateField.getTriggerCron());
        this.assemblyLineRepository.save(assemblyLineEntity);
        return assemblyLineEntity;
    }

}
