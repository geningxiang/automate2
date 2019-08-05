package com.automate.task.background.build.impl;

import com.automate.exec.ExecCommand;
import com.automate.exec.ExecHelper;
import com.automate.task.background.build.IBuildHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/4 19:12
 */
public class ShellHandler implements IBuildHandler {

    private String name;

    /**
     * shell 脚本
     */
    private String scripts;


    @Override
    public boolean execute(Map<String, Object> tempMap, StringBuffer content) {
        try {
            ExecCommand execCommand = new ExecCommand(Arrays.asList(scripts), null, null, null);
            if (execCommand != null) {
                Object baseDir = tempMap.get("baseDir");
                if (baseDir != null) {
                    execCommand.setDir(new File(String.valueOf(baseDir)));
                }
                ExecHelper.exec(execCommand);
                content.append(execCommand.getOut().toString()).append(System.lineSeparator());
                return execCommand.getExitValue() == 0;
            } else {
                content.append("execCommand is blank").append(System.lineSeparator());
                return false;
            }
        } catch (Exception e) {
            content.append(ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    @Override
    public void verify() throws Exception {
        if(StringUtils.isBlank(this.scripts)){
            throw new IllegalArgumentException("scripts is blank");
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScripts() {
        return scripts;
    }

    public void setScripts(String scripts) {
        this.scripts = scripts;
    }
}
