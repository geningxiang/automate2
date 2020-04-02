package com.github.gnx.automate.assemblyline.plugins;

import com.github.gnx.automate.assemblyline.AssemblyLineEnv;
import com.github.gnx.automate.assemblyline.IAssemblyLinePlugin;
import com.github.gnx.automate.assemblyline.IAssemblyLineProgressListener;
import com.github.gnx.automate.assemblyline.field.LocalShellTaskConfig;
import com.github.gnx.automate.common.SystemUtil;
import com.github.gnx.automate.exec.ExecCommand;
import com.github.gnx.automate.exec.ExecHelper;
import com.github.gnx.automate.exec.ExecStreamPrintMonitor;
import com.github.gnx.automate.exec.IExecStreamMonitor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;

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


        File dir = SystemUtil.getProjectSourceCodeDir(assemblyLineEnv.getProjectEntity());

        ExecCommand execCommand = new ExecCommand(Collections.singletonList(taskConfig.getScript()), null, dir, new IExecStreamMonitor() {
            @Override
            public void onStart(String command) {
                listener.onLine(command);
            }

            @Override
            public void onMsg(String line) {
                listener.onLine(line);
            }

            @Override
            public void onError(String line) {
                listener.onLine(line);
            }

            @Override
            public void onEnd(int exitValue) {

            }
        });
        ExecHelper.exec(execCommand);
        return execCommand.getExitValue() == 0;
    }

}
