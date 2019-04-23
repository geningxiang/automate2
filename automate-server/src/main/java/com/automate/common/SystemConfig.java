package com.automate.common;

import com.automate.entity.ProjectEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 项目配置参数      读取  config.PROPERTIES 文件
 *
 * @author: genx
 * @date: 2019/1/29 0:09
 */
public class SystemConfig {
    private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);

    private static String AUTOMATE_DATA_DIR = null;

    private static final Properties PROPERTIES = new Properties();


    static {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource;
        try {
            String configLocation = System.getProperty(SystemContants.PROPERTY_CONFIG_LOCATION);
            if (StringUtils.isNotEmpty(configLocation)) {
                resource = resourceLoader.getResource(configLocation);
            } else {
                logger.warn("当前未自定义配置文件,使用默认配置:{}", SystemContants.DEFAULT_CONFIG_LOCATION);
                resource = resourceLoader.getResource(SystemContants.DEFAULT_CONFIG_LOCATION);
            }
            if (resource != null && resource.exists()) {
                PROPERTIES.load(resource.getInputStream());
            } else {
                logger.error("未读取到配置文件:{}", configLocation);
            }
        } catch (IOException e) {
            logger.error("加载配置文件失败!", e);
        }

        //初始化默认文件夹
        initDataDir();
    }

    /**
     * 获取一个项目的 源码文件夹
     *
     * @param projectEntity
     * @return
     */
    public static String getSourceCodeDir(ProjectEntity projectEntity) {
        if (projectEntity == null || projectEntity.getId() == null) {
            return null;
        }
        Assert.hasText(AUTOMATE_DATA_DIR, "AUTOMATE_DATA_DIR is null");
        return new StringBuilder(AUTOMATE_DATA_DIR).append(File.separator)
                .append(SystemContants.SOURCE_CODE_DIR_NAME).append(File.separator)
                .append(projectEntity.getId()).append(File.separator).toString();
    }

    /**
     * 获取应用包的文件夹路径
     *
     * @param sourceCodeId
     * @return
     */
    public static String getPackageDir(int sourceCodeId) {
        Assert.hasText(AUTOMATE_DATA_DIR, "AUTOMATE_DATA_DIR is null");
        return new StringBuilder(AUTOMATE_DATA_DIR).append(File.separator)
                .append(SystemContants.PACKAGE_DIR_NAME).append(File.separator)
                .append(sourceCodeId).append(File.separator).toString();
    }


    /**
     * 获取 maven 本地仓库地址
     *
     * @return
     */
    public static String getMavenRepositoryDir() {
        String dir = trimToEmpty(PROPERTIES.getProperty(SystemContants.KEY_MAVEN_REPOSITORY));
        Assert.hasText(dir, "maven.repository is not set");
        return dir;
    }

    public static String getProperty(String key) {
        return trimToEmpty(PROPERTIES.getProperty(key));
    }

    /**
     * 将配置信息 输出到控制台
     */
    public static void show() {
        //怎么显示的好看些  计算一下key最大长度 对齐等号     ^_^
        int maxKeyLength = 0;
        for (Map.Entry<Object, Object> entry : PROPERTIES.entrySet()) {
            maxKeyLength = Math.max(maxKeyLength, trimToEmpty(entry.getKey()).length());
        }
        StringBuilder data = new StringBuilder(2048);
        data.append("\r\n");
        String title = "############ System config ############";
        data.append(title).append("\r\n");
        for (Map.Entry<Object, Object> entry : PROPERTIES.entrySet()) {
            int len = trimToEmpty(entry.getKey()).length();
            data.append(trimToEmpty(entry.getKey()));
            data.append(StringUtils.repeat(" ", maxKeyLength - len));
            data.append(" = ");
            data.append(trimToEmpty(entry.getValue()));
            data.append("\r\n");
        }
        data.append(title).append("\r\n\r\n");
        System.out.println(data);
    }

    /**
     * 初始化 项目文件夹
     */
    private static void initDataDir() {
        String automateDataDir = trimToEmpty(PROPERTIES.get(SystemContants.KEY_DATA_DIR));
        if (StringUtils.isNotEmpty(automateDataDir)) {
            automateDataDir = automateDataDir.replace("\\", File.separator);
            if (!automateDataDir.endsWith(File.separator)) {
                automateDataDir += File.separator;
            }
            File dir = new File(automateDataDir);
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                AUTOMATE_DATA_DIR = dir.getAbsolutePath();
            } else if (dir.isDirectory()) {
                AUTOMATE_DATA_DIR = dir.getAbsolutePath();
            } else {
                logger.error("this is not a Directory:{}", automateDataDir);
            }
        } else {
            try {
                ResourceLoader resourceLoader = new DefaultResourceLoader();
                String classResourcePath = SystemConfig.class.getName().replaceAll("\\.", "/") + ".class";
                Resource resource = resourceLoader.getResource(classResourcePath);
                if (resource.isFile()) {
                    //TODO 暂时没有考虑 在 war 的情况
                    String classFilePath = resource.getFile().getAbsolutePath();
                    // 拿到 classes 的路径  比如 E:\tools\apache-tomcat-8.5.32\webapps\ROOT\WEB-INF\classes
                    String classLocation = classFilePath.substring(0, classFilePath.length() - classResourcePath.length());
                     /*
                    从 E:\tools\apache-tomcat-8.5.32\webapps\ROOT\WEB-INF\classes
                    转到 E:\tools\apache-tomcat-8.5.32\automate-data
                     */
                    File webRoot = new File(classLocation).getParentFile().getParentFile().getParentFile().getParentFile();

                    AUTOMATE_DATA_DIR = webRoot.getAbsolutePath() + "/" + SystemContants.DEFAULT_DATA_DIR_NAME;
                    //设置  用于显示
                    PROPERTIES.put(SystemContants.KEY_DATA_DIR, AUTOMATE_DATA_DIR);
                }
            } catch (Exception e) {
                logger.error("init dataDir error", e);
            }
        }
    }

    private static String trimToEmpty(Object obj) {
        return obj == null ? "" : String.valueOf(obj).trim();
    }

    private class SystemContants {
        /**
         * 系统属性中 配置文件路径
         */
        private static final String PROPERTY_CONFIG_LOCATION = "config.location";

        /**
         * 默认的配置文件路径
         * 不推荐直接使用默认配置文件    不利于环境分离
         * PROPERTY_CONFIG_LOCATION 优先级更高
         */
        private static final String DEFAULT_CONFIG_LOCATION = "classpath:config.properties";

        /**
         * 默认的 基础文件夹名称
         */
        private static final String DEFAULT_DATA_DIR_NAME = "automate-data";


        /**
         * 默认的 源代码文件夹名称
         */
        private static final String SOURCE_CODE_DIR_NAME = "sourcecode";

        private static final String PACKAGE_DIR_NAME = "package";

        private static final String KEY_DATA_DIR = "automate.data.dir";
        private static final String KEY_MAVEN_REPOSITORY = "maven.repository";

    }
}
