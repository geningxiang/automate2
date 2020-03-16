package org.automate.automate.repository;

import org.automate.automate.entity.UserEntity;
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
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    /**
     * 根据用户名 查询用户
     *
     * @param userName
     * @return
     */
    UserEntity findFirstByUserName(String userName);
}
