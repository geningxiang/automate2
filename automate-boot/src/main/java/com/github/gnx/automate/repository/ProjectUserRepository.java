package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.ProjectUserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/7/24 21:39
 */
@Repository
public interface ProjectUserRepository extends PagingAndSortingRepository<ProjectUserEntity, Integer>, JpaSpecificationExecutor<ProjectUserEntity> {

}
