package com.automate.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.automate.vo.PathSha1Info;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 项目文件包  war jar apk 用于更新具体应用实例
 * @author genx
 * @date 2019/5/26 20:05
 */
@Entity
@Table(name = "CA2_PROJECT_PACKAGE")
public class ProjectPackageEntity {

    /**
     * 更新类型
     */
    public enum Type{
        /**
         * 未知  只是占个位
         */
        UNKNOWN,
        /**
         * 全量更新
         */
        WHOLE,
        /**
         * 增量更新
         */
        PART
    }

    private int id;

    /**
     * 项目ID
     * @see ProjectEntity#getId()
     */
    private Integer projectId;

    /**
     * 类型 全量更新 还是 增量更新
     * @See Type
     */
    private int type;

    /**
     * 分支
     */
    private String branch;

    /**
     * 最后提交ID
     */
    private String commitId;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 不是很有用 war、zip 每次打包都会有不同的sha1
     */
    private String fileSha1;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 文件列表
     */
    private String fileList;


    /**
     * 应为已该字段作为主要依据
     */
    private String fileListSha1;

    /**
     * 版本号
     * type == BUILD 时 取pom.xml中的版本号
     * type == UPLOAD 时 手工填写
     */
    private String version;

    /**
     * 备注
     */
    private String remark;

    /**
     * 提交人
     * type == BUILD 时 为0
     * type == UPLOAD 时 上传用户ID
     */
    private Integer userId;



    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PROJECT_ID", nullable = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "TYPE", nullable = true)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setType(Type type) {
        if(type != null) {
            this.type = type.ordinal();
        }
    }

    @Basic
    @Column(name = "BRANCH", nullable = true, length = 64)
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Basic
    @Column(name = "COMMIT_ID", nullable = true, length = 64)
    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "FILE_PATH", nullable = true, length = 128)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "SHA1", nullable = true, length = 255)
    public String getFileSha1() {
        return fileSha1;
    }

    public void setFileSha1(String fileSha1) {
        this.fileSha1 = fileSha1;
    }

    @Basic
    @Column(name = "SUFFIX", nullable = true, length = 16)
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Basic
    @Column(name = "FILE_LIST", nullable = true, length = -1)
    public String getFileList() {
        return fileList;
    }

    @Deprecated
    public void setFileList(String fileList) {
        this.fileList = fileList;
    }

    public void setFileList(List<PathSha1Info> list){
        this.fileList = JSON.toJSONString(list);
        Map<String, String> map = new TreeMap();
        for (PathSha1Info pathSha1Info : list) {
            map.put(pathSha1Info.getPath(), pathSha1Info.getSha1());
        }
        this.fileListSha1 = DigestUtils.sha1Hex(JSON.toJSONString(map));
    }

    @Basic
    @Column(name = "FILE_LIST_SHA1", nullable = true, length = 255)
    public String getFileListSha1() {
        return fileListSha1;
    }

    public void setFileListSha1(String fileListSha1) {
        this.fileListSha1 = fileListSha1;
    }

    @Basic
    @Column(name = "VERSION", nullable = true, length = 16)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "REMARK", nullable = true, length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "USER_ID", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }



}
