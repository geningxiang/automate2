package com.automate.common;

import com.automate.entity.ProjectEntity;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 0:09
 */
public class SystemConfig {
    //TODO
    public final static String INSTALLATION_PACKAGE_DIR = "D:/work/temp";

    public static String getProjectSourceCodeDir(ProjectEntity projectEntity){
        Assert.notNull(projectEntity, "projectEntity is null");
        Assert.notNull(projectEntity.getId(), "projectEntity.id is null");
        return "E:/work/" + projectEntity.getId() + "/";
    }
}
