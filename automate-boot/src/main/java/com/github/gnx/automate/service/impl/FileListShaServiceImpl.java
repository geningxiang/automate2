package com.github.gnx.automate.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.gnx.automate.common.file.FileInfo;
import com.github.gnx.automate.common.file.FileListSha256Util;
import com.github.gnx.automate.entity.FileListShaEntity;
import com.github.gnx.automate.repository.FileListShaRepository;
import com.github.gnx.automate.service.IFileListShaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 20:26
 */
@Service
public class FileListShaServiceImpl implements IFileListShaService {

    @Autowired
    private FileListShaRepository fileListShaRepository;


    @Override
    public void save(String sha256, String fileList) {
        FileListShaEntity fileListShaEntity = new FileListShaEntity();
        fileListShaEntity.setSha256(sha256);
        fileListShaEntity.setFileList(fileList);
        fileListShaEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        fileListShaRepository.save(fileListShaEntity);
    }

    @Override
    public List<FileInfo> getFileInfoList(String sha256) {
        FileListShaEntity fileListShaEntity = this.fileListShaRepository.getFirstBySha256(sha256);
        if (fileListShaEntity != null) {
            return FileListSha256Util.parseToList(fileListShaEntity.getFileList());
        } else {
            return new ArrayList(0);
        }

    }

}
