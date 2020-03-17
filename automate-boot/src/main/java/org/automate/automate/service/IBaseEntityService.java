package org.automate.automate.service;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 22:45
 */
public interface IBaseEntityService<T> {
    Iterable<T> findAll();

    /**
     * 查询对象
     **/
    Optional<T> getModel(int id);

    /**
     * 添加对象
     **/
    void save(T model);
}
