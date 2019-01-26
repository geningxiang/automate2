package com.automate.dao;

import com.automate.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:24
 */
@Repository
public interface ProjectDAO extends PagingAndSortingRepository<ProjectEntity, Integer>, JpaSpecificationExecutor<ProjectEntity> {

}
