package com.automate.dao;

import com.automate.entity.ProjectBranchEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/28 23:24
 */
@Repository
public interface ProjectBranchDAO extends PagingAndSortingRepository<ProjectBranchEntity, Integer>, JpaSpecificationExecutor<ProjectBranchEntity> {

    /**
     * 根据 项目ID，分支名称查找
     * @param project
     * @param branchName
     * @return
     */
    ProjectBranchEntity findFirstByProjectIdAndBranchNameOrderByIdDesc(int project, String branchName);
}
