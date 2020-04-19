package com.github.gnx.automate.common;

import com.github.gnx.automate.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 17:39
 */
@Component
public class SystemUtil {


    private static String SOURCE_CODE_DIR = null;

    private static String PACKAGE_DIR = null;

    @Value("${automate.sourceCodeDir:./sourceCode/}")
    public void setSourceCodeDir(String sourceCodeDir) {
        SOURCE_CODE_DIR = sourceCodeDir;
    }

    @Value("${automate.packageDir:./package/}")
    public void setPackageDir(String packageDir) {
        PACKAGE_DIR = packageDir;
    }


    /**
     * 获取一个项目的 源码文件夹
     *
     * @param projectEntity
     * @return
     */
    public static File getProjectSourceCodeDir(ProjectEntity projectEntity) {
        if (projectEntity == null || projectEntity.getId() == null) {
            return null;
        }
        Assert.hasText(SOURCE_CODE_DIR, "AUTOMATE_DATA_DIR is null");
        String path = new StringBuilder(SOURCE_CODE_DIR).append(File.separator)
                .append(projectEntity.getId()).append(File.separator).toString();
        return new File(path);
    }

    public static File getProjectPackageDir(int projectId) {
        String path = new StringBuilder(PACKAGE_DIR).append(File.separator)
                .append(projectId).append(File.separator).toString();
        return new File(path);
    }

}
