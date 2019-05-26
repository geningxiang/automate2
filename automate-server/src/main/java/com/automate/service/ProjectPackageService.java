package com.automate.service;

import com.alibaba.fastjson.JSONArray;
import com.automate.common.SystemConfig;
import com.automate.common.utils.FileListMd5Util;
import com.automate.common.utils.ZipUtil;
import com.automate.entity.ProjectPackageEntity;
import com.automate.repository.ProjectPackageRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ProjectPackageRepository projectPackageRepository;

    public Page<ProjectPackageEntity> findAll(Pageable pageable) {
        return projectPackageRepository.findAll(pageable);
    }

    public ProjectPackageEntity create(int projectId, String version, String branch, String commitId, File file, int userId) throws IOException {
        ProjectPackageEntity projectPackageEntity = new ProjectPackageEntity();
        projectPackageEntity.setProjectId(projectId);
        projectPackageEntity.setBranch(branch);
        projectPackageEntity.setCommitId(commitId);
        projectPackageEntity.setVersion(version);
        projectPackageEntity.setUserId(userId);

        //读取文件列表
        List<FileListMd5Util.PathMd5Info> list = FileListMd5Util.list(file);
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
