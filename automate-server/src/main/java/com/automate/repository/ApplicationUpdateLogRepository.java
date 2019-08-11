package com.automate.repository;

import com.automate.entity.ApplicationUpdateLogEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/11 11:40
 */
public interface ApplicationUpdateLogRepository extends PagingAndSortingRepository<ApplicationUpdateLogEntity, Integer>, JpaSpecificationExecutor<ApplicationUpdateLogEntity> {

}
