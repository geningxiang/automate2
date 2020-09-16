package com.github.gnx.automate.entity;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *  容器
 * @author: genx
 * @date: 2019/2/24 23:38
 */
@Entity
@Table(name = "CA2_CONTAINER")
public class ContainerEntity {

    /**
     * 容器ID
     */
    private Integer id;

    /**
     * 服务器ID
     * @see ServerEntity#getId()
     */
    private Integer serverId;

    /**
     * 项目ID
     * @see ProjectEntity#getId()
     */
    private Integer projectId;

    /**
     * 容器名称
     */
    private String name;

    /**
     * 容器类型
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

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "PROJECT_ID", nullable = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "REMARK", nullable = true, length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "SCRIPT_START", nullable = true, length = 512)
    public String getScriptStart() {
        return scriptStart;
    }

    public void setScriptStart(String scriptStart) {
        this.scriptStart = scriptStart;
    }

    @Basic
    @Column(name = "SCRIPT_STOP", nullable = true, length = 512)
    public String getScriptStop() {
        return scriptStop;
    }

    public void setScriptStop(String scriptStop) {
        this.scriptStop = scriptStop;
    }

    @Basic
    @Column(name = "SCRIPT_CHECK", nullable = true, length = 512)
    public String getScriptCheck() {
        return scriptCheck;
    }

    public void setScriptCheck(String scriptCheck) {
        this.scriptCheck = scriptCheck;
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
    @Column(name = "BASE_DIR", nullable = true)
    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    @Basic
    @Column(name = "SOURCE_DIR", nullable = true)
    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    @Basic
    @Column(name = "UPLOAD_DIR", nullable = true)
    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Basic
    @Column(name = "EXPLODED", nullable = true)
    public Boolean getExploded() {
        return exploded;
    }

    public void setExploded(Boolean exploded) {
        this.exploded = exploded;
    }

    @Basic
    @Column(name = "BACKUP_DIR", nullable = true)
    public String getBackupDir() {
        return backupDir;
    }

    public void setBackupDir(String backupDir) {
        this.backupDir = backupDir;
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("serverId", this.serverId);
        json.put("projectId", this.projectId);
        json.put("name", this.name);
        json.put("type", this.type);
        json.put("remark", this.remark);
        json.put("adminId", this.userId);
        return json;
    }

}
