package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:57
 */
public interface IAssemblyLineLogService {
    Page<AssemblyLineLogEntity> queryPageByProjectId(int projectId, Pageable pageable);
}
