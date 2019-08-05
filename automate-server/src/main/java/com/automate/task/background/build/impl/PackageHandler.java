package com.automate.task.background.build.impl;

import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.ProjectPackageEntity;
import com.automate.service.ProjectPackageService;
import com.automate.task.background.build.IBuildHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/5 23:36
 */
public class PackageHandler implements IBuildHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());


    private final String BASE_DIR = "${baseDir}";

    private String name;

    private String path;

    @Override
    public boolean execute(Map<String, Object> tempMap, StringBuffer content) {
        this.path = path.replace(BASE_DIR, String.valueOf(tempMap.get("baseDir")));

        File file = new File(this.path);
        if (!file.exists()) {
            content.append("未找到相应的文件:").append(this.path);
            return false;
        }

        int projectId = NumberUtils.toInt(String.valueOf(tempMap.get("projectId")));

        if (projectId <= 0) {
            content.append("projectId is error: ").append(projectId);
            return false;
        }
        String version = String.valueOf(tempMap.get("version"));
        String branch = String.valueOf(tempMap.get("branch"));
        String commitId = String.valueOf(tempMap.get("commitId"));


        ProjectPackageService projectPackageService = SpringContextUtil.getBean("projectPackageService", ProjectPackageService.class);

        //TODO 提取文件包
        logger.debug("开始提取文件包: {}", this.path);
        content.append("开始提取文件包:").append(this.path).append(System.lineSeparator());

        try {
            ProjectPackageEntity applicationPackageEntity = projectPackageService.create(projectId, version, branch, commitId, "后台构建生成", file, ProjectPackageEntity.Type.WHOLE, 0);

            content.append("包目标路径:").append(applicationPackageEntity.getFilePath()).append(System.lineSeparator());

            return true;
        } catch (IOException e) {
            content.append(ExceptionUtils.getStackTrace(e));
            return false;
        }


    }

    @Override
    public void verify() throws Exception {
        if (StringUtils.isBlank(this.path)) {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
