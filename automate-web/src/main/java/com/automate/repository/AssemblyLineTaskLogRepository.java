package com.automate.repository;

import com.automate.entity.AssemblyLineTaskLogEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/6 19:20
 */
public interface AssemblyLineTaskLogRepository extends PagingAndSortingRepository<AssemblyLineTaskLogEntity, Integer>, JpaSpecificationExecutor<AssemblyLineTaskLogEntity> {

}
