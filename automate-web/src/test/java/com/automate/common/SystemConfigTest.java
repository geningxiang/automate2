package com.automate.common;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 16:50
 */
public class SystemConfigTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     */
    @Test
    public void test(){
        String classResourcePath = SystemConfig.class.getName().replaceAll("\\.", "/") + ".class";

        URL resource = SystemConfig.class.getClassLoader().getSystemClassLoader().getResource(classResourcePath);

        System.out.println(resource);
        if (resource != null) {
            String urlString = resource.toString();
            logger.debug("The beacon class location is {}.", urlString);

            int insidePathIndex = urlString.indexOf('!');
            boolean isInJar = insidePathIndex > -1;

            if (isInJar) {
                //在jar中
                urlString = urlString.substring(urlString.indexOf("file:"), insidePathIndex);
                File jarFile = null;
                try {
                    jarFile = new File(new URL(urlString).getFile());
                } catch (MalformedURLException e) {
                    logger.error("Can not locate agent jar file by url:" + urlString, e);
                }
                if (jarFile.exists()) {
                    System.out.println(jarFile.getParentFile());
                }
            } else {
                String classLocation = urlString.substring(urlString.indexOf("file:"), urlString.length() - classResourcePath.length());

                classLocation = classLocation.substring(6);
                System.out.println(classLocation);
                File file = new File(classLocation);
                File root = file.getParentFile().getParentFile();
                System.out.println(root.getAbsolutePath());
            }
        }

        logger.error("Can not locate agent jar file.");
    }
}