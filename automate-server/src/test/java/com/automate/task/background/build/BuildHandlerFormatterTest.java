package com.automate.task.background.build;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/4 20:23
 */
public class BuildHandlerFormatterTest {

    @Test
    public void test() throws Exception {
        JSONObject json = new JSONObject();
        json.put("className", "com.automate.task.background.build.impl.ShellHandler");
        json.put("scripts", "ping www.baidu.com");

        IBuildHandler buildHandler = BuildHandlerFormatter.parse(json);
        System.out.println(buildHandler);
    }
}