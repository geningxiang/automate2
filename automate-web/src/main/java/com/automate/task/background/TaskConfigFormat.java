package com.automate.task.background;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/5 20:14
 */
public class TaskConfigFormat {

    public static List<ITask> parse(String config) throws Exception {
        JSONArray array = JSON.parseArray(config);
        List<ITask> tasks = new ArrayList<>(array.size());
        JSONObject item;
        for (int i = 0; i < array.size(); i++) {
            item = array.getJSONObject(i);
            if(item != null) {
                String className = item.getString("className");
                if (StringUtils.isNotEmpty(className)) {
                    Class cls = Class.forName(className);
                    if (ITask.class.isAssignableFrom(cls)) {
                        ITask task = (ITask) JSONObject.parseObject(item.toJSONString(), cls);
                        tasks.add(task);
                    }
                }
            }
        }
        return tasks;
    }

    public static String format(List<ITask> tasks){
        if(tasks == null || tasks.size() == 0){
            return null;
        }
        JSONArray array = new JSONArray(tasks.size());

        for (ITask task : tasks) {
            if(task != null){
                JSONObject item = (JSONObject) JSONObject.toJSON(task);
                item.put("className", task.getClass().getName());
                array.add(item);
            }
        }
        return array.toJSONString();
    }
}
