package com.github.gnx.automate.assemblyline.config;

/**
 * Created with IntelliJ IDEA.
 * Description: 具体的任务
 * @author genx
 * @date 2020/3/29 19:54
 */
public interface IAssemblyLineTaskConfig {

    default String getClassName() {
        return getClass().getName();
    }

    /**
     * 任务名称
     * @return 任务名称
     */
    String getName();

}
