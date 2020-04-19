package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.AssemblyLineTaskLogEntity;
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
public interface AssemblyLineTaskLogRepository extends PagingAndSortingRepository<AssemblyLineTaskLogEntity, Integer>, JpaSpecificationExecutor<AssemblyLineTaskLogEntity> {

    List<AssemblyLineTaskLogEntity> findAllByAssemblyLineLogIdOrderById(int assemblyLineLogId);


}
