package com.automate.task.background.impl;

import com.automate.exec.ExecCommand;
import com.automate.exec.ExecFormatter;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/17 9:51
 */
public class ExecTask extends AbstractExecTask {

    private String scripts;

    @Override
    protected ExecCommand buildExecCommand() throws Exception {
        return new ExecCommand(Arrays.asList(scripts), null, null, null);
    }

    public String getScripts() {
        return scripts;
    }

    public void setScripts(String scripts) {
        this.scripts = scripts;
    }

    @Override
    public void valid() throws Exception {
        ExecFormatter.format(Arrays.asList(scripts));
    }
}
