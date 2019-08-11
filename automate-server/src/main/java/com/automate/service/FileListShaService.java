package com.automate.service;

import com.automate.entity.FileListShaEntity;
import com.automate.repository.FileListShaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/7 23:21
 */
@Service
public class FileListShaService {

    @Autowired
    private FileListShaRepository fileListShaRepository;

    public Optional<FileListShaEntity> findById(String sha1){
        return fileListShaRepository.findById(sha1);
    }


    public void save(String sha256, String fileList) {
        FileListShaEntity fileListShaEntity = new FileListShaEntity();
        fileListShaEntity.setSha256(sha256);
        fileListShaEntity.setFileList(fileList);
        fileListShaEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        fileListShaRepository.save(fileListShaEntity);
    }
}
