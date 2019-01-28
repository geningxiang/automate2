package com.automate.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/28 23:22
 */
@Entity
@Table(name = "CA2_PROJECT_BRANCH")
public class ProjectBranchEntity {
    private Integer id;
    private Integer projectId;
    private String branchName;
    private String lastCommitId;
    private Timestamp lastCommitTime;
    private String lastCommitUser;
    private String commitLog;
    private Timestamp updateTime;

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
    @Column(name = "PROJECT_ID", nullable = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "BRANCH_NAME", nullable = true, length = 64)
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Basic
    @Column(name = "LAST_COMMIT_ID", nullable = true, length = 64)
    public String getLastCommitId() {
        return lastCommitId;
    }

    public void setLastCommitId(String lastCommitId) {
        this.lastCommitId = lastCommitId;
    }

    @Basic
    @Column(name = "LAST_COMMIT_TIME", nullable = true)
    public Timestamp getLastCommitTime() {
        return lastCommitTime;
    }

    public void setLastCommitTime(Timestamp lastCommitTime) {
        this.lastCommitTime = lastCommitTime;
    }

    @Basic
    @Column(name = "LAST_COMMIT_USER", nullable = true, length = 64)
    public String getLastCommitUser() {
        return lastCommitUser;
    }

    public void setLastCommitUser(String lastCommitUser) {
        this.lastCommitUser = lastCommitUser;
    }

    @Basic
    @Column(name = "COMMIT_LOG", nullable = true, length = -1)
    public String getCommitLog() {
        return commitLog;
    }

    public void setCommitLog(String commitLog) {
        this.commitLog = commitLog;
    }

    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

}
