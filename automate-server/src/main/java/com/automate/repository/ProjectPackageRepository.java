package com.automate.repository;

import com.automate.entity.ProjectPackageEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/5 11:04
 */
@Repository
public interface ProjectPackageRepository extends PagingAndSortingRepository<ProjectPackageEntity, Integer>, JpaSpecificationExecutor<ProjectPackageEntity> {

    /**
     * 根据文件的sha1查找包
     * @param sha1
     * @return
     */
    ProjectPackageEntity getFirstByFileSha1OrderByIdDesc(String sha1);


    ProjectPackageEntity getFirstByFileListSha1OrderByIdDesc(String sha1);

}
