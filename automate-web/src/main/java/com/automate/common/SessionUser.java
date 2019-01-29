package com.automate.common;

import com.automate.entity.AdminUserEntity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 23:33
 */
public class SessionUser {
    private AdminUserEntity adminUser;
    private long loginTime;

    public SessionUser(AdminUserEntity adminUser) {
        this.adminUser = adminUser;
        this.loginTime = System.currentTimeMillis();
    }

    public AdminUserEntity getAdminUser() {
        return adminUser;
    }

    public long getLoginTime() {
        return loginTime;
    }
}
