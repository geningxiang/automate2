package com.automate.repository;

import com.automate.entity.ApplicationUpdateApplyEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/11 22:34
 */
@Repository
public interface ApplicationUpdateApplyRepository extends PagingAndSortingRepository<ApplicationUpdateApplyEntity, Integer>, JpaSpecificationExecutor<ApplicationUpdateApplyEntity> {

}
