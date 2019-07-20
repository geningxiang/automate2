package com.automate.service;

import com.alibaba.fastjson.JSONArray;
import com.automate.common.SystemConfig;
import com.automate.common.utils.FileListSha1Util;
import com.automate.common.utils.ZipUtil;
import com.automate.entity.ProjectPackageEntity;
import com.automate.repository.ProjectPackageRepository;
import com.automate.vo.PathSha1Info;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/5 11:09
 */
@Service
public class ProjectPackageService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectPackageRepository projectPackageRepository;

    public Page<ProjectPackageEntity> findAll(Pageable pageable) {
        return projectPackageRepository.findAll(pageable);
    }

    public ProjectPackageEntity create(ProjectPackageEntity model, CommonsMultipartFile fileData) throws IOException {
        System.out.println("fileData.getContentType()=" + fileData.getContentType());
        System.out.println(fileData.getName());
        System.out.println(fileData.getOriginalFilename());
        String fileType = fileData.getOriginalFilename().substring(fileData.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        System.out.println(fileType);
        if ("war".equals(fileType) || "zip".equals(fileType)) {
            //读取文件列表
            File destFile = new File(buildFilePath(model.getProjectId(), fileType));
            fileData.transferTo(destFile);
            List<PathSha1Info> list = FileListSha1Util.list(destFile);
            model.setFileList(JSONArray.toJSONString(list));
            model.setFilePath(destFile.getAbsolutePath());
            model.setSuffix(fileType);
            projectPackageRepository.save(model);
            logger.info("保存更新包,projectId={}, filePath={}", model.getProjectId(), model.getFilePath());
            return model;
        } else {
            throw new IllegalArgumentException("不支持的文件后缀,当前仅支持war、zip");
        }
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
    @Deprecated
    public ProjectPackageEntity create(int projectId, String version, String branch, String commitId, String remark, File file, ProjectPackageEntity.Type type, int userId) throws IOException {
        ProjectPackageEntity projectPackageEntity = buildProjectPackageEntity(projectId, version, branch, commitId, remark, type, userId);

        //读取文件列表
        List<PathSha1Info> list = FileListSha1Util.list(file);
        projectPackageEntity.setFileList(JSONArray.toJSONString(list));

        if (file.isDirectory()) {
            //文件夹 打包成zip
            String fileType = "zip";
            File destFile = new File(buildFilePath(projectId, fileType));
            ZipUtil.compress(file, destFile);
            projectPackageEntity.setFilePath(destFile.getAbsolutePath());
            projectPackageEntity.setSuffix(fileType);
        } else {
            //非文件夹  复制文件
            String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            File destFile = new File(buildFilePath(projectId, fileType));
            FileUtils.copyFile(file, destFile);
            projectPackageEntity.setFilePath(destFile.getAbsolutePath());
            projectPackageEntity.setSuffix(fileType);
        }

        projectPackageEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        projectPackageRepository.save(projectPackageEntity);
        logger.info("保存更新包,projectId={}, filePath={}", projectId, projectPackageEntity.getFilePath());
        return projectPackageEntity;
    }

    private ProjectPackageEntity buildProjectPackageEntity(int projectId, String version, String branch, String commitId, String remark, ProjectPackageEntity.Type type, int userId) {
        ProjectPackageEntity projectPackageEntity = new ProjectPackageEntity();
        projectPackageEntity.setType(type);
        projectPackageEntity.setProjectId(projectId);
        projectPackageEntity.setBranch(StringUtils.trimToEmpty(branch));
        projectPackageEntity.setCommitId(StringUtils.trimToEmpty(commitId));
        projectPackageEntity.setVersion(StringUtils.trimToEmpty(version));
        projectPackageEntity.setRemark(StringUtils.trimToEmpty(remark));
        projectPackageEntity.setUserId(userId);
        projectPackageEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return projectPackageEntity;
    }

    private String buildFilePath(int sourceCodeId, String fileType) {
        //复制文件
        String dirPath = SystemConfig.getPackageDir(sourceCodeId);
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        StringBuilder s = new StringBuilder(128);
        s.append(dirPath);
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
    public synchronized long getCurrentTimeMillis() {
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
