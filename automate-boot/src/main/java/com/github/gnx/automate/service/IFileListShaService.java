package com.github.gnx.automate.service;

import com.github.gnx.automate.common.file.FileInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 20:26
 */
public interface IFileListShaService {

    void save(String sha256, String fileList);


    List<FileInfo> getFileInfoList(String sha256);
}
