package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.common.SystemUtil;
import com.github.gnx.automate.common.exception.AlreadyExistsException;
import com.github.gnx.automate.common.file.FileInfo;
import com.github.gnx.automate.common.file.FileListSha256Util;
import com.github.gnx.automate.common.utils.ZipUtil;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.event.IEventPublisher;
import com.github.gnx.automate.repository.ProductRepository;
import com.github.gnx.automate.service.IFileListShaService;
import com.github.gnx.automate.service.IProductService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 20:15
 */
@Service
public class ProductServiceImpl implements IProductService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IFileListShaService fileListShaService;

    @Override
    public Page<ProductEntity> queryPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<ProductEntity> findById(int id) {
        return productRepository.findById(id);
    }


    @Override
    public List<FileInfo> getFileInfoListById(int productId) {
        ProductEntity productEntity = this.productRepository.findById(productId).get();
        return this.fileListShaService.getFileInfoList(productEntity.getSha256());
    }

    @Override
    public ProductEntity createByBuild(int projectId, String version, String branch, String commitId, String remark, File file) throws IOException {
        String fileType;
        File destFile;
        if (file.isDirectory()) {
            //文件夹 打包成zip
            fileType = "zip";
            destFile = new File(buildFilePath(projectId, fileType));
            ZipUtil.compress(file, destFile);
        } else {
            //非文件夹  复制文件
            fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            destFile = new File(buildFilePath(projectId, fileType));
            FileUtils.copyFile(file, destFile);
        }
        return create(projectId, version, branch, commitId, remark, destFile, ProductEntity.Type.WHOLE, 0);
    }

    /**
     * 保存更新包
     * @param projectId
     * @param version
     * @param branch
     * @param commitId
     * @param remark
     * @param file
     * @param type
     * @param userId
     * @return
     * @throws IOException
     */
    private ProductEntity create(int projectId, String version, String branch, String commitId, String remark, File file, ProductEntity.Type type, int userId) throws IOException {
        //读取文件列表
        List<FileInfo> list = FileListSha256Util.list(file);
        String fileList = FileListSha256Util.parseToString(list);

        String sha256 = DigestUtils.sha256Hex(fileList);

        ProductEntity local = productRepository.getFirstBySha256OrderByIdDesc(sha256);
        if (local != null) {
            logger.warn("sha256已存在, sha: {}, productId: {}", sha256, local.getId());
            throw new AlreadyExistsException(local);
        }

        try {
            //保存 sha256 fileList关系
            this.fileListShaService.save(sha256, fileList);
        } catch (Exception e) {
            logger.warn("文件列表保存失败", e);
        }

        ProductEntity productEntity = buildProjectPackageEntity(projectId, version, branch, commitId, remark, type, userId);
        productEntity.setFilePath(file.getAbsolutePath());
        productEntity.setSha256(sha256);
        String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        productEntity.setSuffix(fileType);
        productRepository.save(productEntity);
        logger.info("保存更新包,projectId={}, filePath={}", projectId, productEntity.getFilePath());
        return productEntity;
    }

    private ProductEntity buildProjectPackageEntity(int projectId, String version, String branch, String commitId, String remark, ProductEntity.Type type, int userId) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setType(type);
        productEntity.setProjectId(projectId);
        productEntity.setBranch(StringUtils.trimToEmpty(branch));
        productEntity.setCommitId(StringUtils.trimToEmpty(commitId));
        productEntity.setVersion(StringUtils.trimToEmpty(version));
        productEntity.setRemark(StringUtils.trimToEmpty(remark));
        productEntity.setUserId(userId);
        productEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return productEntity;
    }

    private String buildFilePath(int projectId, String fileType) {
        //复制文件
        File dir = SystemUtil.getProjectProductDir(projectId);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        StringBuilder s = new StringBuilder(128);
        s.append(dir.getAbsolutePath());
        s.append(File.separatorChar);
        s.append(FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(getCurrentTimeMillis()));
        s.append(".").append(fileType);
        return s.toString();
    }

    private volatile long currentTimeMillis;

    /**
     * 确保单机情况下不会有重复
     *
     * @return
     */
    private synchronized long getCurrentTimeMillis() {
        long now = System.currentTimeMillis();
        if (now == currentTimeMillis) {
            try {
                Thread.sleep(1);
                now = System.currentTimeMillis();
            } catch (InterruptedException e) {
                now++;
            }

        }
        currentTimeMillis = now;
        return now;
    }

}
