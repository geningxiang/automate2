package com.automate.task.background.build.impl;

import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/4 20:03
 */
public class ShellHandlerTest {

    @Test
    public void execute() {

        ShellHandler shellHandler = new ShellHandler();
        shellHandler.setName("shell测试");
        shellHandler.setScripts("ping www.baidu.com\n" +
                "mvn clean package -DskipTests=true");

        StringBuffer content = new StringBuffer(10240);
        Map<String, Object> tempMap = new HashMap();
        tempMap.put("baseDir", new File("D:\\idea-workspace\\automate2\\automate-server").getAbsolutePath());
        boolean result = shellHandler.execute(tempMap, content);

        System.out.println("执行结果:" + result);
        System.out.println(content);
    }

}