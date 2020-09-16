package com.github.gnx.automate.entity;

import com.github.gnx.automate.common.IMsgListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description: 应用更新日志
 * @author genx
 * @date 2019/7/23 23:21
 */
@Entity
@Table(name = "ca2_container_update_log")
public class ContainerUpdateLogEntity implements IMsgListener {
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
     * 容器ID
     */
    private Integer containerId;

    /**
     * 申请ID (0代表直接操作的)
     */
    private Integer applyId;

    /**
     * 产物ID
     */
    private Integer productId;

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
    private StringBuffer log = new StringBuffer(2048);

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
    @Column(name = "CONTAINER_ID", nullable = true)
    public Integer getContainerId() {
        return containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
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
    @Column(name = "PRODUCT_ID", nullable = true)
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public void setStatus(AssemblyLineLogEntity.Status status) {
        this.status = status.ordinal();
    }


    @Basic
    @Column(name = "BEFORE_SHA256", nullable = true, length = 512)
    public String getBeforeSha256() {
        return beforeSha256;
    }

    public void setBeforeSha256(String beforeSha256) {
        this.beforeSha256 = beforeSha256;
    }

    @Basic
    @Column(name = "AFTER_SHA256", nullable = true, length = 512)
    public String getAfterSha256() {
        return afterSha256;
    }

    public void setAfterSha256(String afterSha256) {
        this.afterSha256 = afterSha256;
    }

    @Basic
    @Column(name = "LOG", nullable = true, length = -1)
    public String getLog() {
        return log.toString();
    }

    public void setLog(String log) {
        if(log != null) {
            this.log.append(log);
        }
    }


    @Override
    public IMsgListener append(CharSequence csq) {
        this.log.append(csq);
        return this;
    }
}
