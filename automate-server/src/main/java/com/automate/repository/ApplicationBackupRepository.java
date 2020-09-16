package com.automate.repository;

import com.automate.entity.ApplicationBackupEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/11 21:16
 */
@Repository
public interface ApplicationBackupRepository extends PagingAndSortingRepository<ApplicationBackupEntity, Integer>, JpaSpecificationExecutor<ApplicationBackupEntity> {

}
