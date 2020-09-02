package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.ContainerUpdateLogEntity;
import com.github.gnx.automate.repository.ContainerUpdateLogRepository;
import com.github.gnx.automate.service.IContainerUpdateLogService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/1 20:42
 */
@Service
public class ContainerUpdateLogServiceImpl implements IContainerUpdateLogService {

    private final ContainerUpdateLogRepository containerUpdateLogRepository;

    public ContainerUpdateLogServiceImpl(ContainerUpdateLogRepository containerUpdateLogRepository) {
        this.containerUpdateLogRepository = containerUpdateLogRepository;
    }

    @Override
    public Iterable<ContainerUpdateLogEntity> findAll() {
        return containerUpdateLogRepository.findAll();
    }

}
