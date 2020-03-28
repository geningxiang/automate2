package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<AssemblyLineLogEntity> queryAllByProjectId(int projectId, Pageable pageable);

}
