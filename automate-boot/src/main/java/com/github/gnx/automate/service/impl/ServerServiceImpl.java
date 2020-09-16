package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.ServerEntity;
import com.github.gnx.automate.event.bean.EntityChangeEvent;
import com.github.gnx.automate.event.impl.EventPublisher;
import com.github.gnx.automate.repository.ServerRepository;
import com.github.gnx.automate.service.IServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 14:16
 */
@Service
public class ServerServiceImpl implements IServerService {

    private final ServerRepository serverRepository;

    private final EventPublisher eventPublisher;

    public ServerServiceImpl(ServerRepository serverRepository, EventPublisher eventPublisher) {
        this.serverRepository = serverRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<ServerEntity> findById(int id) {
        return this.serverRepository.findById(id);
    }

    @Override
    public Iterable<ServerEntity> findAll() {
        return this.serverRepository.findAll();
    }

    @Override
    public void save(ServerEntity serverEntity){
        this.serverRepository.save(serverEntity);

        this.eventPublisher.publishEvent(new EntityChangeEvent(serverEntity));
    }

}
