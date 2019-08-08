package com.automate.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/7 23:19
 */
@Entity
@Table(name = "CA2_FILE_LIST_SHA")
public class FileListShaEntity {

    private String sha256;
    private String fileList;
    private Timestamp createTime;

    @Id
    @Column(name = "SHA256", nullable = false, length = 64)
    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    @Basic
    @Column(name = "FILE_LIST", nullable = true, length = -1)
    public String getFileList() {
        return fileList;
    }

    public void setFileList(String fileList) {
        this.fileList = fileList;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


}
