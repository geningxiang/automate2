package com.automate.service;

import com.automate.contants.AdminUserContants;
import com.automate.entity.AdminUserEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 21:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class AdminUserServiceTest {

    @Autowired
    private AdminUserService adminUserService;

    @Test
    public void findAll() {
    }

    @Test
    public void getModel() {
    }

    @Test
    public void save() {

        AdminUserEntity adminUserEntity = new AdminUserEntity();
        adminUserEntity.setUserName("admin");
        adminUserEntity.setPassWord(DigestUtils.md5Hex("admin"));
        adminUserEntity.setRealName("超级管理员");
        adminUserEntity.setLevel(AdminUserContants.ADMIN_USER_LEVEL_ROOT);
        adminUserEntity.setStatus(AdminUserEntity.Status.ACTIVATE);
        adminUserService.save(adminUserEntity);
    }
}