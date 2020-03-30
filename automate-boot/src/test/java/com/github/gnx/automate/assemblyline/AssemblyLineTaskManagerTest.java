package com.github.gnx.automate.assemblyline;

import com.alibaba.fastjson.JSON;
import com.github.gnx.automate.assemblyline.field.AssemblyLineTask;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 22:20
 */
public class AssemblyLineTaskManagerTest {

    private AssemblyLineTaskManager assemblyLineTaskManager = new AssemblyLineTaskManager();

    @Test
    public void test() throws InterruptedException {


        String s = "{\n" +
                "    \"projectId\": 1,\n" +
                "    \"branch\": \"master\",\n" +
                "    \"name\": \"日常构建\",\n" +
                "    \"stepTasks\": [\n" +
                "        {\n" +
                "            \"stepName\": \"构建阶段\",\n" +
                "            \"specificTasks\": [\n" +
                "                {\n" +
                "                    \"className\": \"com.github.gnx.automate.assemblyline.field.LocalShellTaskConfig\",\n" +
                "                    \"name\": \"查询本机IP\",\n" +
                "                    \"script\": \"ipconfig\",\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        AssemblyLineTask assemblyLineTask = JSON.parseObject(s, AssemblyLineTask.class);

        assemblyLineTaskManager.execute(assemblyLineTask);

        Thread.sleep(1000);

    }

}