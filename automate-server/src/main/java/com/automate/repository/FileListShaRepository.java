package com.automate.repository;

import com.automate.entity.FileListShaEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/7 23:20
 */
public interface FileListShaRepository extends PagingAndSortingRepository<FileListShaEntity, String>, JpaSpecificationExecutor<FileListShaEntity> {


}
