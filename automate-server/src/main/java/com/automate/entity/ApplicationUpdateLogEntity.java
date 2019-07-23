package com.automate.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/7/23 23:21
 */
@Entity
@Table(name = "CA2_APPLICATION_UPDATE_LOG")
public class ApplicationUpdateLogEntity {
    private Integer id;

    /**
     * 项目ID
     */
    private Integer projectId;

    /**
     * 服务器ID
     */
    private Integer serverId;

    /**
     * 应用ID
     */
    private Integer applicationId;

    /**
     * 申请ID (0代表直接操作的)
     */
    private Integer applyId;

    /**
     * 操作用户ID
     */
    private Integer userId;

    /**
     * 类型 0更新 1回滚
     */
    private Integer type;

    /**
     * 开始时间
     */
    private Timestamp createTime;

    /**
     * 更新完成时间
     */
    private Timestamp doneTime;

    private Integer status;

    /**
     * 更新前的 fileList
     */
    private String beforeFileList;

    /**
     * 更新前的 SHA1
     */
    private String beforeSha1;

    /**
     * 更新后的 fileList
     */
    private String afterFileList;

    /**
     * 更新后的 SHA1
     */
    private String afterSha1;

    /**
     * 更新日志
     */
    private String log;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "APPLICATION_ID", nullable = true)
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
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
    @Column(name = "SERVER_ID", nullable = true)
    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    @Basic
    @Column(name = "APPLY_ID", nullable = true)
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    @Basic
    @Column(name = "USER_ID", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "TYPE", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
    @Column(name = "DONE_TIME", nullable = true)
    public Timestamp getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Timestamp doneTime) {
        this.doneTime = doneTime;
    }

    @Basic
    @Column(name = "STATUS", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "BEFORE_FILE_LIST", nullable = true, length = -1)
    public String getBeforeFileList() {
        return beforeFileList;
    }

    public void setBeforeFileList(String beforeFileList) {
        this.beforeFileList = beforeFileList;
    }

    @Basic
    @Column(name = "BEFORE_SHA1", nullable = true, length = 255)
    public String getBeforeSha1() {
        return beforeSha1;
    }

    public void setBeforeSha1(String beforeSha1) {
        this.beforeSha1 = beforeSha1;
    }

    @Basic
    @Column(name = "AFTER_FILE_LIST", nullable = true, length = -1)
    public String getAfterFileList() {
        return afterFileList;
    }

    public void setAfterFileList(String afterFileList) {
        this.afterFileList = afterFileList;
    }

    @Basic
    @Column(name = "AFTER_SHA1", nullable = true, length = 255)
    public String getAfterSha1() {
        return afterSha1;
    }

    public void setAfterSha1(String afterSha1) {
        this.afterSha1 = afterSha1;
    }

    @Basic
    @Column(name = "LOG", nullable = true, length = -1)
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
