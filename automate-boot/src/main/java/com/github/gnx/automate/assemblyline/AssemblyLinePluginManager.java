package com.github.gnx.automate.assemblyline;

import com.github.gnx.automate.assemblyline.field.ITaskConfig;
import com.github.gnx.automate.assemblyline.field.LocalShellTaskConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 22:50
 */
@Component
public class AssemblyLinePluginManager {

    private static Map<Class<? extends ITaskConfig>, IAssemblyLinePlugin> pluginMap = new HashMap(64);

    public static boolean execute(AssemblyLineRunnable.AssemblyLineEnv assemblyLineEnv, ITaskConfig taskConfig, IAssemblyLineProgressListener listener) throws Exception {
        IAssemblyLinePlugin assemblyLinePlugin = pluginMap.get(taskConfig.getClass());
        if (assemblyLinePlugin == null) {
            throw new RuntimeException("未找到相应的插件: " + taskConfig.getClass().getName());
        }
        return assemblyLinePlugin.execute(assemblyLineEnv, taskConfig, listener);
    }

    @Autowired
    public void setPluginMap(IAssemblyLinePlugin[] assemblyLinePlugins) {
        if (assemblyLinePlugins != null) {
            for (IAssemblyLinePlugin assemblyLinePlugin : assemblyLinePlugins) {
                pluginMap.put(assemblyLinePlugin.getTaskConfigClass(), assemblyLinePlugin);
            }
        }
    }
}
