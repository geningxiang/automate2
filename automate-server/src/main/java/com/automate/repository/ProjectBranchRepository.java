package com.automate.repository;

import com.automate.entity.ProjectBranchEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/28 23:24
 */
@Repository
public interface ProjectBranchRepository extends PagingAndSortingRepository<ProjectBranchEntity, Integer>, JpaSpecificationExecutor<ProjectBranchEntity> {

    /**
     * 根据 源码库ID，分支名称查找
     *
     * @param projectId
     * @param branchName
     * @return
     */
    ProjectBranchEntity findFirstByProjectIdAndBranchName(int projectId, String branchName);

    /**
     *
     * @param projectId
     * @return
     */
    List<ProjectBranchEntity> getAllByProjectIdOrderByLastCommitTime(int projectId);
}
