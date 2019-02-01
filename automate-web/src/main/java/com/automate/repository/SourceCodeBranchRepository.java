package com.automate.repository;

import com.automate.entity.SourceCodeBranchEntity;
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
public interface SourceCodeBranchRepository extends PagingAndSortingRepository<SourceCodeBranchEntity, Integer>, JpaSpecificationExecutor<SourceCodeBranchEntity> {

    /**
     * 根据 源码库ID，分支名称查找
     *
     * @param sourceId
     * @param branchName
     * @return
     */
    SourceCodeBranchEntity findFirstBySourceCodeIdAndBranchName(int sourceId, String branchName);
}
