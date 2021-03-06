package com.automate.repository;

import com.automate.entity.AssemblyLineLogEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/6 19:15
 */
@Repository
public interface AssemblyLineLogRepository extends PagingAndSortingRepository<AssemblyLineLogEntity, Integer>, JpaSpecificationExecutor<AssemblyLineLogEntity> {


}
