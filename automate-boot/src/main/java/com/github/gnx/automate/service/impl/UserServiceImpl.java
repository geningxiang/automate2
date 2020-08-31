package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.UserEntity;
import com.github.gnx.automate.repository.UserRepository;
import com.github.gnx.automate.service.IUserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 21:52
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserRepository userRepository;


    @Override
    public UserEntity findNormalUserByKey(String key) {
        Assert.hasText(key, "userName is null");
        return this.userRepository.findNormalUserByKey(key);
    }


    @Override
    public Iterable<UserEntity> findAll() {
        return userRepository.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<UserEntity> findById(int id) {
        return userRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(UserEntity model) {
        userRepository.save(model);
    }


}
