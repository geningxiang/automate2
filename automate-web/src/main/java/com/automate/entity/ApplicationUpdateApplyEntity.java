package com.automate.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/10 22:46
 */
@Entity
@Table(name = "ca2_application_update_apply")
public class ApplicationUpdateApplyEntity {

    public enum Status{
        APPLY,
        ADOPT,
        REJECT
    }

    private int id;
    private Integer sourceCodeId;
    private Integer packageId;
    /**
     * 容器IDS
     */
    private String containerIds;
    private Integer createAdminId;
    private Timestamp createTime;
    private Byte status;
    private Integer auditAdminId;
    private Timestamp auditTime;

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
    @Column(name = "PACKAGE_ID", nullable = true)
    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    @Basic
    @Column(name = "CONTAINER_IDS", nullable = true, length = 128)
    public String getContainerIds() {
        return containerIds;
    }

    public void setContainerIds(String containerIds) {
        this.containerIds = containerIds;
    }

    @Basic
    @Column(name = "CREATE_ADMIN_ID", nullable = true)
    public Integer getCreateAdminId() {
        return createAdminId;
    }

    public void setCreateAdminId(Integer createAdminId) {
        this.createAdminId = createAdminId;
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
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "AUDIT_ADMIN_ID", nullable = true)
    public Integer getAuditAdminId() {
        return auditAdminId;
    }

    public void setAuditAdminId(Integer auditAdminId) {
        this.auditAdminId = auditAdminId;
    }

    @Basic
    @Column(name = "AUDIT_TIME", nullable = true)
    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

}
