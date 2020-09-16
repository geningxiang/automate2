package com.automate.repository;

import com.automate.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/17 23:49
 */
@Repository
public interface ServerRepository extends PagingAndSortingRepository<ServerEntity, Integer>, JpaSpecificationExecutor<ServerEntity> {

}
