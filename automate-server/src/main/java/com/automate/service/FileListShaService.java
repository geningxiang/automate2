package com.automate.service;

import com.automate.entity.FileListShaEntity;
import com.automate.repository.FileListShaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
