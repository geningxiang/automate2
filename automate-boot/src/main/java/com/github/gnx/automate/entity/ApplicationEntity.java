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
@Table(name = "CA2_APPLICATION")
public class ApplicationEntity {
    private Integer id;
    private Integer serverId;
    private Integer projectId;
    private String name;
    private Integer type;
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
     * 备份文件夹 ${baseDir}/backup
     */
    private String backupDir;

    private String scriptStart;
    private String scriptStop;
    private String scriptCheck;
    private Integer adminId;

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
    @Column(name = "ADMIN_ID", nullable = true)
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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
        json.put("adminId", this.adminId);
        return json;
    }

}
