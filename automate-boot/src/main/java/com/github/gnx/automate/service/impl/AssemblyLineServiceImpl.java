package com.github.gnx.automate.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.field.req.AssemblyLineSaveField;
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
    public AssemblyLineEntity create(AssemblyLineSaveField assemblyLineSaveField, int projectId, int userId) {
        AssemblyLineEntity assemblyLineEntity = new AssemblyLineEntity();
        assemblyLineEntity.setProjectId(projectId);
        assemblyLineEntity.setBranches(assemblyLineSaveField.getBranches());
        assemblyLineEntity.setName(assemblyLineSaveField.getName());
        assemblyLineEntity.setRemark(assemblyLineSaveField.getRemark());
        assemblyLineEntity.setConfig(JSONArray.toJSONString(assemblyLineSaveField.getStepTasks()));
        assemblyLineEntity.setUserId(userId);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        assemblyLineEntity.setCreateTime(now);
        assemblyLineEntity.setUpdateTime(now);
        assemblyLineEntity.setLastRunTime(null);
        assemblyLineEntity.setAutoTrigger(assemblyLineSaveField.getAutoTrigger());
        assemblyLineEntity.setTriggerCron(assemblyLineSaveField.getTriggerCron());
        this.assemblyLineRepository.save(assemblyLineEntity);
        return assemblyLineEntity;
    }

    @Override
    public AssemblyLineEntity update(int assemblyLineId, AssemblyLineSaveField assemblyLineSaveField){
        Optional<AssemblyLineEntity> optionalAssemblyLineEntity = this.assemblyLineRepository.findById(assemblyLineId);
        if(!optionalAssemblyLineEntity.isPresent()){
            throw new IllegalArgumentException("未找到相应的流水线, id: " + assemblyLineId);
        }

        AssemblyLineEntity assemblyLineEntity = optionalAssemblyLineEntity.get();

        assemblyLineEntity.setBranches(assemblyLineSaveField.getBranches());
        assemblyLineEntity.setName(assemblyLineSaveField.getName());
        assemblyLineEntity.setRemark(assemblyLineSaveField.getRemark());
        assemblyLineEntity.setConfig(JSONArray.toJSONString(assemblyLineSaveField.getStepTasks()));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        assemblyLineEntity.setUpdateTime(now);
        assemblyLineEntity.setAutoTrigger(assemblyLineSaveField.getAutoTrigger());
        assemblyLineEntity.setTriggerCron(assemblyLineSaveField.getTriggerCron());

        this.assemblyLineRepository.save(assemblyLineEntity);
        return assemblyLineEntity;
    }

    private void checkStepTasks(AssemblyLineSaveField assemblyLineSaveField){
        //TODO 检查 流水线配置
    }

}
