package com.automate.service;

import com.alibaba.fastjson.JSONArray;
import com.automate.common.utils.FileListMd5Util;
import com.automate.entity.ApplicationPackageEntity;
import com.automate.repository.ApplicationPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<FileListMd5Util.PathMd5Info> list = FileListMd5Util.list(file);
        applicationPackageEntity.setFileTree(JSONArray.toJSONString(list));
        applicationPackageEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

        //TODO 复制文件


        applicationPackageRepository.save(applicationPackageEntity);
    }
}
