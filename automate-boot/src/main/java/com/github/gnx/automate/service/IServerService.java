package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.ServerEntity;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 14:16
 */
public interface IServerService {
    Optional<ServerEntity> findById(int id);

    Iterable<ServerEntity> findAll();

    void save(ServerEntity serverEntity);
}
