package com.github.gnx.automate.vo.response;

import com.github.gnx.automate.entity.AssemblyLineLogEntity;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/1 20:59
 */
public class ContainerUpdateLogVO {

    private Integer id;

    /**
     * 项目ID
     */
    private Integer projectId;

    private String projectName;

    /**
     * 服务器ID
     */
    private Integer serverId;

    private String serverName;

    /**
     * 容器ID
     */
    private Integer containerId;

    private String containerName;

    /**
     * 申请ID (0代表直接操作的)
     */
    private Integer applyId;

    /**
     * 操作用户ID
     */
    private Integer userId;

    private String userName;

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

    /**
     * @see AssemblyLineLogEntity.Status
     */
    private Integer status;

    /**
     * 更新前的 SHA256
     */
    private String beforeSha256;


    /**
     * 更新后的 SHA256
     */
    private String afterSha256;

    /**
     * 更新日志
     */
    private String log;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getContainerId() {
        return containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Timestamp doneTime) {
        this.doneTime = doneTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBeforeSha256() {
        return beforeSha256;
    }

    public void setBeforeSha256(String beforeSha256) {
        this.beforeSha256 = beforeSha256;
    }

    public String getAfterSha256() {
        return afterSha256;
    }

    public void setAfterSha256(String afterSha256) {
        this.afterSha256 = afterSha256;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
