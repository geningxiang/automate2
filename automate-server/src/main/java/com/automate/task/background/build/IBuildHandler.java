package com.automate.task.background.build;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/4 19:10
 */
public interface IBuildHandler {

    String getName();

    boolean execute(Map<String, Object> tempMap, StringBuffer content);

    /**
     * 验证
     * 从json转IBuildHandler  检查一下参数是否正常
     */
    void verify() throws Exception;
}
