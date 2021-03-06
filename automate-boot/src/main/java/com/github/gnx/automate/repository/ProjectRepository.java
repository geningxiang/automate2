package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.ProjectEntity;
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
public interface ProjectRepository extends PagingAndSortingRepository<ProjectEntity, Integer>, JpaSpecificationExecutor<ProjectEntity> {

    /**
     * 根据 版本控制地址查找 源码库
     *
     * @param vcsUrl
     * @return
     */
    ProjectEntity getFirstByVcsUrl(String vcsUrl);

    ProjectEntity getFirstByNameOrVcsUrl(String name, String vcsUrl);
}
