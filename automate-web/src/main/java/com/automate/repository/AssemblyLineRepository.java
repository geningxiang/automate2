package com.automate.repository;

import com.automate.entity.AssemblyLineEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/4 16:22
 */
@Repository
public interface AssemblyLineRepository extends PagingAndSortingRepository<AssemblyLineEntity, Integer>, JpaSpecificationExecutor<AssemblyLineEntity> {

}
