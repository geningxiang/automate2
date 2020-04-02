package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.AssemblyLineTaskLogEntity;
import com.github.gnx.automate.repository.AssemblyLineTaskLogRepository;
import com.github.gnx.automate.service.IAssemblyLineTaskLogService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/2 22:02
 */
@Service
public class AssemblyLineTaskLogServiceImpl implements IAssemblyLineTaskLogService {

    private final AssemblyLineTaskLogRepository assemblyLineTaskLogRepository;

    public AssemblyLineTaskLogServiceImpl(AssemblyLineTaskLogRepository assemblyLineTaskLogRepository) {
        this.assemblyLineTaskLogRepository = assemblyLineTaskLogRepository;
    }


    @Override
    public AssemblyLineTaskLogEntity save(AssemblyLineTaskLogEntity assemblyLineTaskLogEntity) {
        assemblyLineTaskLogRepository.save(assemblyLineTaskLogEntity);
        return assemblyLineTaskLogEntity;
    }

}
