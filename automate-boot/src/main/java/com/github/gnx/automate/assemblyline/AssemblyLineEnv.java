package com.github.gnx.automate.assemblyline;

import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.ProjectEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 23:23
 */
public class AssemblyLineEnv {

    public enum BuiltInProperty{
        baseDir,
        projectId,
        branch,
        commitId

    }


    private final ProjectEntity projectEntity;

    private final AssemblyLineLogEntity assemblyLineLogEntity;

    /**
     * 如果是maven gradle 类的项目 读取 配置文件中的版本号
     */
    private String version = "";

    private Map<String, Object> data = new HashMap(32);

    public AssemblyLineEnv(ProjectEntity projectEntity, AssemblyLineLogEntity assemblyLineLogEntity){
        this.projectEntity = projectEntity;
        this.assemblyLineLogEntity = assemblyLineLogEntity;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public AssemblyLineLogEntity getAssemblyLineLogEntity() {
        return assemblyLineLogEntity;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }
}
