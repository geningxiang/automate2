package com.github.gnx.automate.assemblyline;

import com.github.gnx.automate.entity.ProjectEntity;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 23:23
 */
public class AssemblyLineEnv {

    private ProjectEntity projectEntity;

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }
}
