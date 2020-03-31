package com.github.gnx.automate.assemblyline.field;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/29 19:53
 */
public class AssemblyLineStepTask {

    private String stepName;

    private List<ITaskConfig> tasks;


    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public List<ITaskConfig> getTasks() {
        return tasks;
    }

    public void setTasks(List<?> tasks) throws ClassNotFoundException {
        if (tasks == null) {
            this.tasks = null;
        }

        List<ITaskConfig> list = new ArrayList(tasks.size());
        for (Object item : tasks) {
            if (item != null) {

                if (item instanceof ITaskConfig) {
                    list.add((ITaskConfig) item);
                } else if (item instanceof JSONObject) {
                    //将 json格式解析到具体的实体类， json必须包含 className 标识具体类名
                    String className = ((JSONObject) item).getString("className");
                    if(StringUtils.isBlank(className)){
                        throw new RuntimeException("JSON解析失败:" + className + "不是有效的任务类");
                    }
                    Class cls = Class.forName(className);
                    if (!ITaskConfig.class.isAssignableFrom(cls)) {
                        throw new RuntimeException("JSON解析失败:" + className + "不是有效的任务类");
                    }

                    ITaskConfig specificTask = (ITaskConfig) JSON.parseObject(((JSONObject) item).toJSONString(), cls);
                    list.add(specificTask);
                } else {
                    throw new RuntimeException("JSON解析失败:" + item);
                }

            }

        }
        this.tasks = list;

    }


}
