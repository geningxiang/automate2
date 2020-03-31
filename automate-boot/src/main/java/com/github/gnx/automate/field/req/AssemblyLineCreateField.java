package com.github.gnx.automate.field.req;

import com.github.gnx.automate.assemblyline.field.AssemblyLineStepTask;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/31 21:04
 */
public class AssemblyLineCreateField {

    /**
     * 允许的分支
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
    @Size(min=1, message = "请至少添加一个步骤")
    private List<AssemblyLineStepTask> stepTasks;


    /**
     * 是否自动触发
     */
    private Boolean autoTrigger;

    /**
     * 定时触发 cron 表达式
     */
    private String triggerCron;


    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<AssemblyLineStepTask> getStepTasks() {
        return stepTasks;
    }

    public void setStepTasks(List<AssemblyLineStepTask> stepTasks) {
        this.stepTasks = stepTasks;
    }

    public Boolean getAutoTrigger() {
        return autoTrigger;
    }

    public void setAutoTrigger(Boolean autoTrigger) {
        this.autoTrigger = autoTrigger;
    }

    public String getTriggerCron() {
        return triggerCron;
    }

    public void setTriggerCron(String triggerCron) {
        this.triggerCron = triggerCron;
    }
}
