package com.automate.repository;

import com.automate.entity.SourceCodeEntity;
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
public interface SourceCodeRepository extends PagingAndSortingRepository<SourceCodeEntity, Integer>, JpaSpecificationExecutor<SourceCodeEntity> {

    /**
     * 根据 版本控制地址查找 源码库
     *
     * @param vcsUrl
     * @return
     */
    SourceCodeEntity getFirstByVcsUrl(String vcsUrl);
}
