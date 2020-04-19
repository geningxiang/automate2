package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 14:17
 */
public interface ServerRepository extends PagingAndSortingRepository<ServerEntity, Integer>, JpaSpecificationExecutor<ServerEntity> {

}
