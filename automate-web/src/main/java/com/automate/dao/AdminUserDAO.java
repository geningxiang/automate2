package com.automate.dao;

import com.automate.entity.AdminUserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 0:57
 */
@Repository
public interface AdminUserDAO extends PagingAndSortingRepository<AdminUserEntity, Integer>, JpaSpecificationExecutor<AdminUserEntity> {

    /**
     * 根据用户名 查询用户
     *
     * @param userName
     * @return
     */
    AdminUserEntity findFirstByUserName(String userName);
}
