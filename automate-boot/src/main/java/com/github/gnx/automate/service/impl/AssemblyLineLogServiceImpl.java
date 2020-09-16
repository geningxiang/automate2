package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.repository.AssemblyLineLogRepository;
import com.github.gnx.automate.service.IAssemblyLineLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:57
 */
@Service
public class AssemblyLineLogServiceImpl implements IAssemblyLineLogService {

    private final AssemblyLineLogRepository assemblyLineLogRepository;

    public AssemblyLineLogServiceImpl(AssemblyLineLogRepository assemblyLineLogRepository) {
        this.assemblyLineLogRepository = assemblyLineLogRepository;
    }

    @Override
    public Page<AssemblyLineLogEntity> queryPageByProjectId(int projectId, Pageable pageable) {
        return assemblyLineLogRepository.queryAllByProjectId(projectId, pageable);
    }

    @Override
    public AssemblyLineLogEntity saveWithAssemblyLine(AssemblyLineEntity assemblyLineEntity, String branch, String commitId, int userId) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        AssemblyLineLogEntity assemblyLineLogEntity = new AssemblyLineLogEntity();
        assemblyLineLogEntity.setAssemblyLineId(assemblyLineEntity.getId());
        assemblyLineLogEntity.setProjectId(assemblyLineEntity.getProjectId());
        assemblyLineLogEntity.setName(assemblyLineEntity.getName());
        assemblyLineLogEntity.setBranch(branch);
        assemblyLineLogEntity.setCommitId(commitId);
        assemblyLineLogEntity.setConfig(assemblyLineEntity.getConfig());
        assemblyLineLogEntity.setCreateUserId(userId);
        assemblyLineLogEntity.setStartTime(now);
        assemblyLineLogEntity.setEndTime(null);
        assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.INIT);
        assemblyLineLogEntity.setCreateTime(now);
        this.assemblyLineLogRepository.save(assemblyLineLogEntity);
        return assemblyLineLogEntity;
    }


    @Override
    public AssemblyLineLogEntity update(AssemblyLineLogEntity model) {
        this.assemblyLineLogRepository.save(model);
        return model;
    }

    @Override
    public Optional<AssemblyLineLogEntity> findById(int assemblyLineLogId){
        return this.assemblyLineLogRepository.findById(assemblyLineLogId);
    }

}
