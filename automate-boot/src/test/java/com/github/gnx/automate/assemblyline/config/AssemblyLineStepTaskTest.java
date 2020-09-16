package com.github.gnx.automate.assemblyline.config;


import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/29 19:56
 */
public class AssemblyLineStepTaskTest {

    @Test
    public void test(){

        String s = "{\n" +
                "    \"stepName\": \"构建阶段\",\n" +
                "    \"specificTasks\": [\n" +
                "        {\n" +
                "            \"className\": \"com.github.gnx.automate.assemblyline.field.ShellTask\",\n" +
                "            \"name\": \"mvn package\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";


        AssemblyLineStepTask assemblyLineStepTask = JSON.parseObject(s, AssemblyLineStepTask.class);

        System.out.println(assemblyLineStepTask.getStepName());

        if(assemblyLineStepTask.getTasks() != null){

            for (IAssemblyLineTaskConfig specificTask : assemblyLineStepTask.getTasks()) {
                System.out.println(specificTask.getClass().getName());
            }

        }

    }



}