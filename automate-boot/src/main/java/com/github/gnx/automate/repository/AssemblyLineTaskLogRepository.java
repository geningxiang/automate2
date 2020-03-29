package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.AssemblyLineStepLogEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/6 19:20
 */
public interface AssemblyLineTaskLogRepository extends PagingAndSortingRepository<AssemblyLineStepLogEntity, Integer>, JpaSpecificationExecutor<AssemblyLineStepLogEntity> {

    List<AssemblyLineStepLogEntity> findAllByAssemblyLineLogIdOrderById(int assemblyLineLogId);
}
