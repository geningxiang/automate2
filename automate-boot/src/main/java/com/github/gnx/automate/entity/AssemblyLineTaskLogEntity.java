package com.github.gnx.automate.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * Description: 流水线 单步执行日志
 * @author: genx
 * @date: 2019/2/4 16:06
 */
@Entity
@Table(name = "CA2_ASSEMBLY_LINE_TASK_LOG")
public class AssemblyLineTaskLogEntity {

    private Integer id;

    /**
     * 项目ID
     */
    private Integer projectId;

    /**
     * 流水线ID
     * @see AssemblyLineLogEntity#getId()
     */
    private Integer assemblyLineLogId;

    /**
     * 阶段的下标
     */
    private int stepIndex;

    /**
     * 具体任务的下标
     */
    private int taskIndex;

    private StringBuffer content;

    /**
     * @see AssemblyLineLogEntity.Status
     */
    private Integer status;

    private Timestamp startTime;
    private Timestamp endTime;

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
    @Column(name = "PROJECT_ID", nullable = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
    @Column(name = "STEP_INDEX", nullable = true)
    public int getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(int lifeCycle) {
        this.stepIndex = lifeCycle;
    }

    @Basic
    @Column(name = "TASK_INDEX", nullable = true)
    public int getTaskIndex() {
        return taskIndex;
    }

    public void setTaskIndex(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Basic
    @Column(name = "CONTENT", nullable = true, length = -1)
    public String getContent() {
        return this.content != null ? content.toString() : "";
    }

    public void setContent(String content) {
        this.content = new StringBuffer(content);
    }

    public void appendLine(String content) {
        if (this.content == null) {
            synchronized (this) {
                if (this.content == null) {
                    this.content = new StringBuffer(102400);
                }
            }
        }
        this.content.append(content).append(System.lineSeparator());
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
