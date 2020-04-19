package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.FileListShaEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 20:22
 */
public interface FileListShaRepository extends PagingAndSortingRepository<FileListShaEntity, Integer>, JpaSpecificationExecutor<FileListShaEntity> {


    FileListShaEntity getFirstBySha256(String sha256);

}
