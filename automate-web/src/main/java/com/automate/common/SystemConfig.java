package com.automate.common;

import com.automate.entity.ProjectEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 0:09
 */
public class SystemConfig {
    private static final Logger logger = LoggerFactory.getLogger(SystemConfig.class);

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


    private static final Properties properties = new Properties();

    static {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource;
        try {
            String configLocation = System.getProperty(PROPERTY_CONFIG_LOCATION);
            if (StringUtils.isNotEmpty(configLocation)) {
                resource = resourceLoader.getResource(configLocation);
            } else {
                logger.warn("当前未自定义配置文件,使用默认配置:{}", DEFAULT_CONFIG_LOCATION);
                resource = resourceLoader.getResource(DEFAULT_CONFIG_LOCATION);
            }
            if (resource != null && resource.exists()) {
                properties.load(resource.getInputStream());
            } else {
                logger.error("未读取到配置文件:{}", configLocation);
            }
        } catch (IOException e) {
            logger.error("加载配置文件失败!", e);
        }
    }

    public static void show() {
        //怎么显示的好看些  计算一下key最大长度 对齐等号     ^_^
        int maxKeyLength = 0;
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            maxKeyLength = Math.max(maxKeyLength, trimToEmpty(entry.getKey()).length());
        }
        StringBuilder data = new StringBuilder(2048);
        data.append("\r\n");
        String title = "############ System config ############";
        data.append(title).append("\r\n");
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
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


    //TODO
    public final static String INSTALLATION_PACKAGE_DIR = "D:/work/temp";


    private static String trimToEmpty(Object obj) {
        return obj == null ? "" : String.valueOf(obj).trim();
    }


    /**
     * 获取一个项目的 源码文件夹
     *
     * @param projectEntity
     * @return
     */
    public static String getProjectSourceCodeDir(ProjectEntity projectEntity) {
        Assert.notNull(projectEntity, "projectEntity is null");
        Assert.notNull(projectEntity.getId(), "projectEntity.id is null");
        return "E:/work/" + projectEntity.getId() + "/";
    }


    public static String getMavenRepositoryDir() {
        //TODO
        return "D:/Program Files/apache-maven-3.5.3/repository1";
    }

    public static void main(String[] args) throws IOException {


        SystemConfig.show();
    }

}
