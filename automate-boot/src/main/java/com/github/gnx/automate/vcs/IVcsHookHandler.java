package com.github.gnx.automate.vcs;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/30 23:55
 */
public interface IVcsHookHandler {

    boolean match(Map<String, String> headers, JSONObject data);

    void doHandle(Map<String, String> headers, JSONObject data);

}
