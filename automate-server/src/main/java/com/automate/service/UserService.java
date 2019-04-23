package com.automate.service;

import com.automate.repository.UserRepository;
import com.automate.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 21:52
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity findFirstByUserName(String userName){
        Assert.hasText(userName, "userName is null");
        return this.userRepository.findFirstByUserName(userName);
    }

    public Iterable<UserEntity> findAll() {
        return userRepository.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<UserEntity> getModel(int id) {
        return userRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(UserEntity model) {
        userRepository.save(model);
    }


}
