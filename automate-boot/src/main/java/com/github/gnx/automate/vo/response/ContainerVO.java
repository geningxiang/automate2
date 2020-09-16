package com.github.gnx.automate.vo.response;

import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.entity.ServerEntity;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/31 23:17
 */
public class ContainerVO {

    /**
     * 容器ID
     */
    private Integer id;

    /**
     * 服务器ID
     * @see ServerEntity#getId()
     */
    private Integer serverId;

    private String serverName;

    /**
     * 项目ID
     * @see ProjectEntity#getId()
     */
    private Integer projectId;

    private String projectName;

    /**
     * 容器名称
     */
    private String name;

    /**
     * 容器类型
     *
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 基础文件夹
     */
    private String baseDir;

    /**
     * 源码文件夹   比如 tomcat ${baseDir}/webroot/ROOT
     */
    private String sourceDir;

    /**
     * 更新包上传文件夹
     * ${baseDir}/upload
     */
    private String uploadDir;

    /**
     * 是否需要解压
     * Tomcat 容器一般 解压部署
     * SpringBoot 直接放jar
     */
    private Boolean exploded;

    /**
     * 备份文件夹
     * 每次更新将原先代码打包到备份文件夹下
     * ${baseDir}/backup
     */
    private String backupDir;

    /**
     * 启动脚本
     */
    private String scriptStart;

    /**
     * 停止脚本
     */
    private String scriptStop;

    /**
     * 检查脚本
     */
    private String scriptCheck;

    /**
     * 容器所有者
     * 有事找他就对了
     */
    private Integer userId;

    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public Boolean getExploded() {
        return exploded;
    }

    public void setExploded(Boolean exploded) {
        this.exploded = exploded;
    }

    public String getBackupDir() {
        return backupDir;
    }

    public void setBackupDir(String backupDir) {
        this.backupDir = backupDir;
    }

    public String getScriptStart() {
        return scriptStart;
    }

    public void setScriptStart(String scriptStart) {
        this.scriptStart = scriptStart;
    }

    public String getScriptStop() {
        return scriptStop;
    }

    public void setScriptStop(String scriptStop) {
        this.scriptStop = scriptStop;
    }

    public String getScriptCheck() {
        return scriptCheck;
    }

    public void setScriptCheck(String scriptCheck) {
        this.scriptCheck = scriptCheck;
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
