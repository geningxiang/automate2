package com.automate.repository;

import com.automate.entity.ContainerEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/27 20:31
 */
@Repository
public interface ContainerRepository extends PagingAndSortingRepository<ContainerEntity, Integer>, JpaSpecificationExecutor<ContainerEntity> {

}
