package com.automate.service;

import com.alibaba.fastjson.JSONArray;
import com.automate.common.SystemConfig;
import com.automate.common.utils.FileListMd5Util;
import com.automate.common.utils.ZipUtil;
import com.automate.entity.ApplicationPackageEntity;
import com.automate.repository.ApplicationPackageRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
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
public class ApplicationPackageService {

    @Autowired
    private ApplicationPackageRepository applicationPackageRepository;

    public void create(int sourceCodeId, String version, String branch, String commitId, File file, int adminId) throws IOException {
        ApplicationPackageEntity applicationPackageEntity = new ApplicationPackageEntity();
        applicationPackageEntity.setSourceCodeId(sourceCodeId);
        applicationPackageEntity.setBranch(branch);
        applicationPackageEntity.setCommitId(commitId);
        applicationPackageEntity.setVersion(version);
        applicationPackageEntity.setAdminId(adminId);

        //读取文件列表
        List<FileListMd5Util.PathMd5Info> list = FileListMd5Util.list(file);
        applicationPackageEntity.setFileTree(JSONArray.toJSONString(list));



        if(file.isDirectory()){
            //文件夹 打包成zip
            String fileType = "zip";
            File destFile = new File(buildFilePath(sourceCodeId, fileType));
            ZipUtil.compress(file, destFile);
            applicationPackageEntity.setPackagePath(destFile.getAbsolutePath());
            applicationPackageEntity.setPackageType(fileType);
        } else {
            //非文件夹  复制文件
            String fileType = file.getName().substring(file.getName().lastIndexOf(".") +1);
            File destFile = new File(buildFilePath(sourceCodeId, fileType));
            FileUtils.copyFile(file, destFile);
            applicationPackageEntity.setPackagePath(destFile.getAbsolutePath());
            applicationPackageEntity.setPackageType(fileType);
        }

        applicationPackageEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        applicationPackageRepository.save(applicationPackageEntity);
    }

    private String buildFilePath(int sourceCodeId, String fileType){
        //复制文件
        String dirPath = SystemConfig.getPackageDir(sourceCodeId);
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        StringBuilder s = new StringBuilder(128);
        s.append(dirPath);
        //TODO 小范围使用不会有啥问题
        s.append(FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(getCurrentTimeMillis()));
        s.append(".").append(fileType);
        return s.toString();
    }

    private volatile long currentTimeMillis;

    /**
     * 确保单机情况下不会有重复
     * @return
     */
    public synchronized long getCurrentTimeMillis(){
        long now = System.currentTimeMillis();
        if(now == currentTimeMillis) {
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
