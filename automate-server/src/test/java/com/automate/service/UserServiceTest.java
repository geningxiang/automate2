package com.automate.service;

import com.automate.contants.AdminUserContants;
import com.automate.entity.UserEntity;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findAll() {
    }

    @Test
    public void getModel() {
    }

    @Test
    public void save() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("admin");
        userEntity.setPassWord(DigestUtils.md5Hex("admin"));
        userEntity.setRealName("超级管理员");
        userEntity.setLevel(AdminUserContants.ADMIN_USER_LEVEL_ROOT);
        userEntity.setStatus(UserEntity.Status.ACTIVATE);
        userService.save(userEntity);
    }
}