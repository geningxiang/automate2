package com.github.gnx.automate.assemblyline.field;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/29 19:52
 */
public class AssemblyLineTask {

    private int assemblyLineId;

    private int projectId;

    private String branch;

    private String name;

    private List<AssemblyLineStepTask> stepTasks;

    public int getAssemblyLineId() {
        return assemblyLineId;
    }

    public void setAssemblyLineId(int assemblyLineId) {
        this.assemblyLineId = assemblyLineId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AssemblyLineStepTask> getStepTasks() {
        return stepTasks;
    }

    public void setStepTasks(List<AssemblyLineStepTask> stepTasks) {
        this.stepTasks = stepTasks;
    }
}
