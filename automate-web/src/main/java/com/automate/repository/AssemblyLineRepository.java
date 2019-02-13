package com.automate.repository;

import com.automate.entity.AssemblyLineEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/4 16:22
 */
@Repository
public interface AssemblyLineRepository extends PagingAndSortingRepository<AssemblyLineEntity, Integer>, JpaSpecificationExecutor<AssemblyLineEntity> {

    List<AssemblyLineEntity> getAllBySourceCodeId(int sourceCodeId);

    List<AssemblyLineEntity> getAllBySourceCodeIdAndAutoTrigger(int sourceCodeId, boolean autoTrigger);
}
