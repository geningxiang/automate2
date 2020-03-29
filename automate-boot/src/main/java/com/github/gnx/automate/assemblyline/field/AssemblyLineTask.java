package com.github.gnx.automate.assemblyline.field;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/29 19:52
 */
public class AssemblyLineTask {

    private String name;

    private List<AssemblyLineStepTask> stepTasks;

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
