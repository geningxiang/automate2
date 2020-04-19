package com.github.gnx.automate.assemblyline;

import com.github.gnx.automate.assemblyline.config.IAssemblyLineTaskConfig;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/29 13:19
 */
public interface IAssemblyLinePlugin<T extends IAssemblyLineTaskConfig> {

    /**
     * 插件关联配置信息的类
     * @return Class<? extends IAssemblyLineTaskConfig>
     */
    Class<T> getTaskConfigClass();

    /**
     * 执行具体任务
     * @param assemblyLineEnv 当前流水线环境
     * @param taskConfig 任务配置信息
     * @param listener 监听器
     * @return boolean 是否执行成功
     * @throws Exception 异常
     */
    boolean execute(AssemblyLineEnv assemblyLineEnv, T taskConfig, IAssemblyLineProgressListener listener) throws Exception;

    /**
     * 需要的参数
     * input() 于 export() 一起 判断配置的流水线是否完整
     * @return String[]
     */
    String[] input();

    /**
     * 输出的参数
     * @return String[]
     */
    String[] export();


}
