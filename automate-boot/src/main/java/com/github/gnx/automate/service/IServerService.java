package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.ServerEntity;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 14:16
 */
public interface IServerService {
    Iterable<ServerEntity> findAll();
}
