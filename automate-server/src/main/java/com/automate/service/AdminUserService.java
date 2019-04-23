package com.automate.service;

import com.automate.repository.AdminUserRepository;
import com.automate.entity.AdminUserEntity;
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
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    public AdminUserEntity findFirstByUserName(String userName){
        Assert.hasText(userName, "userName is null");
        return this.adminUserRepository.findFirstByUserName(userName);
    }

    public Iterable<AdminUserEntity> findAll() {
        return adminUserRepository.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<AdminUserEntity> getModel(int id) {
        return adminUserRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(AdminUserEntity model) {
        adminUserRepository.save(model);
    }


}
