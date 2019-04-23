package com.automate.common;

import com.automate.entity.UserEntity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 23:33
 */
public class SessionUser {
    private UserEntity adminUser;
    private long loginTime;

    public SessionUser(UserEntity adminUser) {
        this.adminUser = adminUser;
        this.loginTime = System.currentTimeMillis();
    }

    public UserEntity getAdminUser() {
        return adminUser;
    }

    public long getLoginTime() {
        return loginTime;
    }
}
