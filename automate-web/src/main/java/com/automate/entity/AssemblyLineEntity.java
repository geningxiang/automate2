package com.automate.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 流水线
 *
 * @author: genx
 * @date: 2019/2/4 16:06
 */
@Entity
@Table(name = "CA2_ASSEMBLY_LINE")
public class AssemblyLineEntity {

    private Integer id;
    /**
     * 代码仓库ID
     *
     * @see SourceCodeEntity#getId()
     */
    private Integer sourceCodeId;
    /**
     * 分支名称
     */
    private String branches;
    /**
     * 流水线名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 配置  用于描述这条流水线的 子任务
     */
    private String config;
    /**
     * 拥有者
     */
    private Integer adminId;

    /**
     * 是否自动触发
     */
    private Boolean autoTrigger;

    /**
     * 定时触发 cron 表达式
     */
    private String triggerCron;

    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp lastRunTime;


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
    @Column(name = "SOURCE_CODE_ID", nullable = true)
    public Integer getSourceCodeId() {
        return sourceCodeId;
    }

    public void setSourceCodeId(Integer sourceCodeId) {
        this.sourceCodeId = sourceCodeId;
    }

    @Basic
    @Column(name = "BRANCHES", nullable = true, length = 256)
    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
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
    @Column(name = "REMARK", nullable = true, length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    @Column(name = "CREATE_TIME", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "LAST_RUN_TIME", nullable = true)
    public Timestamp getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(Timestamp lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    @Basic
    @Column(name = "AUTO_TRIGGER", nullable = false)
    public Boolean getAutoTrigger() {
        return autoTrigger;
    }

    public void setAutoTrigger(Boolean autoTrigger) {
        this.autoTrigger = autoTrigger;
    }

    @Basic
    @Column(name = "TRIGGER_CRON", nullable = true, length = 64)
    public String getTriggerCron() {
        return triggerCron;
    }

    public void setTriggerCron(String triggerCron) {
        this.triggerCron = triggerCron;
    }

}
