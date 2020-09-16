package com.automate.vo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/13 22:29
 */
public class FileListShaSearchVO {

    private String key;
    private String sha256;
    private List fileList;

    public FileListShaSearchVO(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public List getFileList() {
        return fileList;
    }

    public void setFileList(List fileList) {
        this.fileList = fileList;
    }
}
