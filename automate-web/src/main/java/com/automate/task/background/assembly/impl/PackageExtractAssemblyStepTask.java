package com.automate.task.background.assembly.impl;

import com.automate.common.SystemConfig;
import com.automate.task.background.assembly.AbstractAssemblyStepTask;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 安装包提取
 *
 * @author: genx
 * @date: 2019/3/5 20:57
 */
public class PackageExtractAssemblyStepTask extends AbstractAssemblyStepTask {

    private final String BASE_DIR = "${baseDir}";

    private String path;

    public String getPath() {
        if(this.path.contains(BASE_DIR)){
            return this.path.replace(BASE_DIR, SystemConfig.getSourceCodeDir(getSourceCodeEntity()));
        } else {
            return this.path;
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void valid() throws Exception {

    }

    @Override
    public boolean doInvoke() throws Exception {

        String path = getPath();
        File file = new File(path);
        System.out.println(file.getAbsolutePath());

        if(!file.exists()){
            throw new IOException("文件不存在:" + file.getAbsolutePath());
        }


        if(file.isDirectory()){
            throw new IOException("暂不支持文件夹,等待后续完善");
        }

        int index = file.getName().lastIndexOf(".");
        if(index <= 0){
            throw new IOException("文件没有后缀?" + file.getName());
        }

        String suffix = file.getName().substring(index + 1).toLowerCase();

//        if(!"war".equals(suffix)){
//            throw new IOException("暂时只支持war后缀,等待后续完善");
//        }

        //TODO 提取文件包
        logger.debug("开始提取文件包: {}", file.getAbsolutePath());
        appendContent("开始提取文件包:");
        appendContent(file.getAbsolutePath());
        return true;

    }


}
