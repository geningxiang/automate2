package com.automate.repository;

import com.automate.entity.HookLogEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:25
 */
@Repository
public interface HookLogRepository extends PagingAndSortingRepository<HookLogEntity, Integer>, JpaSpecificationExecutor<HookLogEntity> {

}
