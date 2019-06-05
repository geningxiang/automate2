package com.automate.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/5/26 20:36
 */
@Entity
@Table(name = "CA2_APPLICATION_UPDATE_APPLY")
public class ApplicationUpdateApplyEntity {

    public enum Status {
        /**
         * 申请中
         */
        APPLY,
        /**
         * 通过
         */
        ADOPT,
        /**
         * 拒绝
         */
        REJECT
    }

    private int id;

    /**
     * 项目ID
     * @see ProjectEntity#getId()
     */
    private Integer projectId;

    /**
     * 项目包ID
     * @see ProjectPackageEntity#getId()
     */
    private Integer projectPackageId;

    /**
     * 容器ID
     * @see ContainerEntity#getId()
     */
    private Integer containerId;

    /**
     * 容器代码文件的 MD5列表
     */
    private String containerFileMd5List;

    /**
     * 根据md5List 计算得到的 sha1  用于不同容器相同程序文件的检索
     */
    private String containerFileSha1;

    /**
     * 发起用户
     */
    private Integer createUserId;
    private Timestamp createTime;

    /**
     * 装填
     * @see Status
     */
    private int status;

    /**
     * 审核人
     */
    private Integer auditUserId;
    /**
     * 审核时间
     */
    private Timestamp auditTime;
    /**
     * 审核结果
     */
    private String auditResult;

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
    @Column(name = "PROJECT_PACKAGE_ID", nullable = true)
    public Integer getProjectPackageId() {
        return projectPackageId;
    }

    public void setProjectPackageId(Integer projectPackageId) {
        this.projectPackageId = projectPackageId;
    }

    @Basic
    @Column(name = "CONTAINER_ID", nullable = true)
    public Integer getContainerId() {
        return containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }

    @Basic
    @Column(name = "CONTAINER_FILE_MD5_LIST", nullable = true, length = -1)
    public String getContainerFileMd5List() {
        return containerFileMd5List;
    }

    public void setContainerFileMd5List(String containerFileMd5List) {
        this.containerFileMd5List = containerFileMd5List;
    }

    @Basic
    @Column(name = "CONTAINER_FILE_SHA1", nullable = true, length = 64)
    public String getContainerFileSha1() {
        return containerFileSha1;
    }

    public void setContainerFileSha1(String containerFileSha1) {
        this.containerFileSha1 = containerFileSha1;
    }

    @Basic
    @Column(name = "CREATE_USER_ID", nullable = true)
    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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
    @Column(name = "STATUS", nullable = true)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus(Status status) {
        this.status = status.ordinal();
    }

    @Basic
    @Column(name = "AUDIT_USER_ID", nullable = true)
    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
    }

    @Basic
    @Column(name = "AUDIT_TIME", nullable = true)
    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    @Basic
    @Column(name = "AUDIT_RESULT", nullable = true, length = 256)
    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }


}
