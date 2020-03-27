package com.github.gnx.automate.repository;

import com.github.gnx.automate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    @Query("select u from UserEntity u where u.status = 1 and ( u.userName = ?1 or u.mobile = ?1 or u.email = ?1) ")
    UserEntity findNormalUserByKey(String key);
}
