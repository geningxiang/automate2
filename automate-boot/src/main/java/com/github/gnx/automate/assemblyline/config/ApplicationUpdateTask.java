package com.github.gnx.automate.assemblyline.config;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 20:04
 */
public class ApplicationUpdateTask implements IAssemblyLineTaskConfig {

    private String name;

    /**
     * 容器ID
     */
    private Integer containerId;


    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getContainerId() {
        return containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }
}
