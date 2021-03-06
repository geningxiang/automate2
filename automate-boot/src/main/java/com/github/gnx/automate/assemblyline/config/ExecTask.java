package com.github.gnx.automate.assemblyline.config;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/29 19:55
 */
public class ExecTask implements IAssemblyLineTaskConfig {

    @NotBlank(message = "请输入任务名")
    private String name;

    @NotBlank(message = "请输入脚本内容")
    private String script;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
