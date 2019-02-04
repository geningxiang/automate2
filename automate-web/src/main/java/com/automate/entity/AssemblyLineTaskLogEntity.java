package com.automate.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/4 16:06
 */
@Entity
@Table(name = "CA2_ASSEMBLY_LINE_TASK_LOG")
public class AssemblyLineTaskLogEntity {
    private int id;
    private Integer sourceCodeId;
    private String branch;
    private String commitId;
    private Integer serverId;
    private Integer applicationId;
    private Integer assemblyLineLogId;
    private Short type;
    private Short kind;
    private String content;
    private Short status;
    private Timestamp startTime;
    private Timestamp endTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SOURCE_CODE_ID", nullable = true)
    public Integer getSourceCodeId() {
        return sourceCodeId;
    }

    public void setSourceCodeId(Integer sourceCodeId) {
        this.sourceCodeId = sourceCodeId;
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
    @Column(name = "SERVER_ID", nullable = true)
    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
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
    @Column(name = "ASSEMBLY_LINE_LOG_ID", nullable = true)
    public Integer getAssemblyLineLogId() {
        return assemblyLineLogId;
    }

    public void setAssemblyLineLogId(Integer assemblyLineLogId) {
        this.assemblyLineLogId = assemblyLineLogId;
    }

    @Basic
    @Column(name = "TYPE", nullable = true)
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Basic
    @Column(name = "KIND", nullable = true)
    public Short getKind() {
        return kind;
    }

    public void setKind(Short kind) {
        this.kind = kind;
    }

    @Basic
    @Column(name = "CONTENT", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "STATUS", nullable = true)
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

}
