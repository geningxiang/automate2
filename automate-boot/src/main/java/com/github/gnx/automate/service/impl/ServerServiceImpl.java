package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.ServerEntity;
import com.github.gnx.automate.repository.ServerRepository;
import com.github.gnx.automate.service.IServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 14:16
 */
@Service
public class ServerServiceImpl implements IServerService {

    @Autowired
    private ServerRepository serverRepository;

    @Override
    public Iterable<ServerEntity> findAll(){
        return this.serverRepository.findAll();
    }

}
