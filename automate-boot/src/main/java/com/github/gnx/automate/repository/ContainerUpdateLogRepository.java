package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.ContainerUpdateLogEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/29 19:58
 */
@Repository
public interface ContainerUpdateLogRepository extends PagingAndSortingRepository<ContainerUpdateLogEntity, Integer>, JpaSpecificationExecutor<ContainerUpdateLogEntity> {

}
