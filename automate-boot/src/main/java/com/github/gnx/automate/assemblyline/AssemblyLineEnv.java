package com.github.gnx.automate.assemblyline;

import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.exec.IExecConnection;
import com.github.gnx.automate.exec.IExecTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 23:23
 */
public class AssemblyLineEnv {

    public enum BuildInProperty {
        baseDir,
        projectId,
        branch,
        commitId

    }


    /**
     * 项目对象
     */
    private final ProjectEntity projectEntity;

    /**
     * 流水线日志对象
     */
    private final AssemblyLineLogEntity assemblyLineLogEntity;

    /**
     * exec 执行环境
     * 本地shell执行
     * 远程docker执行
     */
    private final IExecConnection execConnection;


    /**
     * 如果是maven gradle 类的项目 读取 配置文件中的版本号
     */
    private String version = "";

    private Map<String, Object> data = new HashMap(32);

    public AssemblyLineEnv(ProjectEntity projectEntity, AssemblyLineLogEntity assemblyLineLogEntity, IExecConnection execConnection) {
        this.projectEntity = projectEntity;
        this.assemblyLineLogEntity = assemblyLineLogEntity;
        this.execConnection = execConnection;
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
