package org.automate.automate.service;

import org.automate.automate.entity.UserEntity;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 22:44
 */
public interface IUserService extends IBaseEntityService<UserEntity> {

    /**
     * 查找用户
     * @param key 用户名/手机号码/邮箱
     * @return
     */
    UserEntity findNormalUserByKey(String key);
}
