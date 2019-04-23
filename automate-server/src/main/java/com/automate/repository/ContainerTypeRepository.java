package com.automate.repository;

import com.automate.entity.ContainerTypeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/27 20:32
 */
@Repository
public interface ContainerTypeRepository extends PagingAndSortingRepository<ContainerTypeEntity, Integer>, JpaSpecificationExecutor<ContainerTypeEntity> {

}
