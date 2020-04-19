package com.github.gnx.automate.assemblyline.plugins;

import com.github.gnx.automate.assemblyline.AssemblyLineEnv;
import com.github.gnx.automate.assemblyline.IAssemblyLinePlugin;
import com.github.gnx.automate.assemblyline.IAssemblyLineProgressListener;
import com.github.gnx.automate.assemblyline.config.ExecTask;
import com.github.gnx.automate.common.SystemUtil;
import com.github.gnx.automate.exec.ExecCommand;
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
public class LocalShellPlugin implements IAssemblyLinePlugin<ExecTask> {

    @Override
    public Class<ExecTask> getTaskConfigClass() {
        return ExecTask.class;
    }

    @Override
    public boolean execute(AssemblyLineEnv assemblyLineEnv, ExecTask taskConfig, IAssemblyLineProgressListener listener) throws Exception {


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
//        ExecHelper.exec(execCommand);
        return execCommand.getExitValue() == 0;
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
