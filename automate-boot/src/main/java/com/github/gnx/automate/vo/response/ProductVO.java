package com.github.gnx.automate.vo.response;

import com.github.gnx.automate.entity.FileListShaEntity;
import com.github.gnx.automate.entity.ProjectEntity;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/31 23:34
 */
public class ProductVO {

    private int id;

    /**
     * 项目ID
     * @see ProjectEntity#getId()
     */
    private Integer projectId;

    private String projectName;

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
     * 文件后缀
     */
    private String suffix;


    /**
     * 根据 fileList 计算出来的 sha256
     * @see FileListShaEntity#getSha256()
     */
    private String sha256;

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

    private String userName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
