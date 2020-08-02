package com.github.gnx.automate.service;

import com.github.gnx.automate.common.file.FileInfo;
import com.github.gnx.automate.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 20:15
 */
public interface IProductService {
    Page<ProductEntity> queryPage(Pageable pageable);

    Optional<ProductEntity> findById(int id);

    List<FileInfo> getFileInfoListById(int productId);

    ProductEntity createByBuild(int projectId, String version, String branch, String commitId, String remark, File file) throws IOException;
}
