package com.automate.task.background.build;

import com.alibaba.fastjson.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/4 20:14
 */
public class BuildHandlerFormatter {

    public static IBuildHandler parse(JSONObject json) throws Exception {

        String className = json.getString("className");
        if(className == null){
            throw new RuntimeException("className is required");
        }
        Class cls = Class.forName(className);
        if (IBuildHandler.class.isAssignableFrom(cls)) {
            IBuildHandler task = (IBuildHandler) JSONObject.parseObject(json.toJSONString(), cls);
            if(task != null){
                task.verify();
                return task;
            }
        } else {
            throw new RuntimeException(cls.getName() + " is not extend IBuildHandler");
        }
        return null;
    }
}
