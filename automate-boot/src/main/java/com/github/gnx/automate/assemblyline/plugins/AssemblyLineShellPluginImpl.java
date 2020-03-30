package com.github.gnx.automate.assemblyline.plugins;

import com.github.gnx.automate.assemblyline.AssemblyLineEnv;
import com.github.gnx.automate.assemblyline.IAssemblyLinePlugin;
import com.github.gnx.automate.assemblyline.IAssemblyLineProgressListener;
import com.github.gnx.automate.assemblyline.field.LocalShellTaskConfig;
import com.github.gnx.automate.exec.ExecCommand;
import com.github.gnx.automate.exec.ExecHelper;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 22:27
 */
@Component
public class AssemblyLineShellPluginImpl implements IAssemblyLinePlugin<LocalShellTaskConfig> {

    @Override
    public Class<LocalShellTaskConfig> getTaskConfigClass() {
        return LocalShellTaskConfig.class;
    }

    @Override
    public boolean execute(AssemblyLineEnv assemblyLineEnv, LocalShellTaskConfig taskConfig, IAssemblyLineProgressListener listener) throws Exception {
        ExecCommand execCommand = new ExecCommand(taskConfig.getScript());
        ExecHelper.exec(execCommand);
        return execCommand.getExitValue() == 0;
    }

}
