package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:57
 */
public interface IAssemblyLineLogService {
    Page<AssemblyLineLogEntity> queryPageByProjectId(int projectId, Pageable pageable);

    AssemblyLineLogEntity saveWithAssemblyLine(AssemblyLineEntity assemblyLineEntity, String branch, String commitId, int userId);

    AssemblyLineLogEntity update(AssemblyLineLogEntity model);

    Optional<AssemblyLineLogEntity> findById(int assemblyLineLogId);
}
