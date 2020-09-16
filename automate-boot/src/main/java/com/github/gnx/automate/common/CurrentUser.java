package com.github.gnx.automate.common;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 22:53
 */
public class CurrentUser {

    private int userId;

    private String userToken;

    /**
     * 项目权限
     * { 项目ID: 权限等级 }
     */
    private Map<Integer, Integer> projectMap;

    public CurrentUser(int userId){
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
