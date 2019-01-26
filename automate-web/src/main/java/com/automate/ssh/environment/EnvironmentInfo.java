package com.automate.ssh.environment;

import com.alibaba.fastjson.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/2 22:35
 */
public class EnvironmentInfo {

    /**
     * 主路径
     */
    private String dir;

    /**
     * 版本号
     */
    private String version;

    /**
     * 配置信息
     */
    private JSONObject config;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public JSONObject getConfig() {
        return config;
    }

    public void setConfig(JSONObject config) {
        this.config = config;
    }
}
