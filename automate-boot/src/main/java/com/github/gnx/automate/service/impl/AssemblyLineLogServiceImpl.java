package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.repository.AssemblyLineLogRepository;
import com.github.gnx.automate.service.IAssemblyLineLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:57
 */
@Service
public class AssemblyLineLogServiceImpl implements IAssemblyLineLogService {

    @Autowired
    private AssemblyLineLogRepository assemblyLineLogRepository;

    @Override
    public Page<AssemblyLineLogEntity> queryPageByProjectId(int projectId, Pageable pageable) {
        return assemblyLineLogRepository.queryAllByProjectId(projectId, pageable);
    }

}
