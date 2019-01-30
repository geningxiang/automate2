package com.automate.repository;

import com.automate.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:24
 */
@Repository
public interface ProjectRepository extends PagingAndSortingRepository<ProjectEntity, Integer>, JpaSpecificationExecutor<ProjectEntity> {

    /**
     * 根据 版本控制地址查找项目
     * @param versionUrl
     * @return
     */
    List<ProjectEntity> getAllByVersionUrlOrderByIdDesc(String versionUrl);
}
