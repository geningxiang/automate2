package com.github.gnx.automate.assemblyline.config;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/3 13:32
 */
public class ProductPickTask implements IAssemblyLineTaskConfig {

    @NotBlank(message = "请输入任务名")
    private String name;

    @NotBlank(message = "请输入脚本")
    private String filePath;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
