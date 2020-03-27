package com.github.gnx.automate.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 流水线执行日志
 *
 * @author: genx
 * @date: 2019/2/4 16:06
 */
@Entity
@Table(name = "CA2_ASSEMBLY_LINE_LOG")
public class AssemblyLineLogEntity {

    public enum Status{
        init,
        running,
        cancel,
        error,
        success
    }


    private Integer id;
    private Integer assemblyLineId;
    private Integer projectId;
    private String branch;
    private String commitId;
    private String config;
    private Integer adminId;
    private Timestamp createTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private Byte status;

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
    @Column(name = "ASSEMBLY_LINE_ID", nullable = true)
    public Integer getAssemblyLineId() {
        return assemblyLineId;
    }

    public void setAssemblyLineId(Integer assemblyLineId) {
        this.assemblyLineId = assemblyLineId;
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
    @Column(name = "CONFIG", nullable = true, length = -1)
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
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
    @Column(name = "START_TIME", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "END_TIME", nullable = true)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "STATUS", nullable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = (byte)status.ordinal();
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
