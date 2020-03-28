package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.repository.ContainerRepository;
import com.github.gnx.automate.service.IContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:51
 */
@Service
public class ContainerServiceImpl implements IContainerService {

    @Autowired
    private ContainerRepository containerRepository;

    @Override
    public List<ContainerEntity> getAllByProjectIdOrderById(int projectId){
        return containerRepository.getAllByProjectIdOrderById(projectId);
    }


}
