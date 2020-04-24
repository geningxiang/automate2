package com.github.gnx.automate.assemblyline.plugins;

import com.github.gnx.automate.assemblyline.AssemblyLineEnv;
import com.github.gnx.automate.assemblyline.IAssemblyLinePlugin;
import com.github.gnx.automate.assemblyline.config.ExecTask;
import com.github.gnx.automate.common.IMsgListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 22:27
 */
@Component
public class ShellExecPlugin implements IAssemblyLinePlugin<ExecTask> {

    @Override
    public Class<ExecTask> getTaskConfigClass() {
        return ExecTask.class;
    }

    @Override
    public boolean execute(AssemblyLineEnv env, ExecTask taskConfig, IMsgListener listener) throws Exception {
        String cmd = "cd " + env.getBaseDir() + " && " + taskConfig.getScript();
        return env.getExecConnection().exec(cmd, listener) == 0;
    }

    @Override
    public String[] input() {
        return null;
    }

    @Override
    public String[] export() {
        return null;
    }


}
