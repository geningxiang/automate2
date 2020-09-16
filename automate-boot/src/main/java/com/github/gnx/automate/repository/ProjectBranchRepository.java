package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.ProjectBranchEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 21:58
 */
@Repository
public interface ProjectBranchRepository extends PagingAndSortingRepository<ProjectBranchEntity, Integer>, JpaSpecificationExecutor<ProjectBranchEntity> {

    ProjectBranchEntity getFirstByProjectIdAndBranchName(int projectId, String branchName);

    List<ProjectBranchEntity> getAllByProjectIdOrderByLastCommitTimeDescId(int projectId);
}
