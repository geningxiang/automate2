package com.automate.service;

import com.automate.entity.ContainerEntity;
import com.automate.entity.HookLogEntity;
import com.automate.entity.ServerEntity;
import com.automate.repository.ContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/27 20:32
 */
@Service
public class ContainerService {

    @Autowired
    private ContainerRepository containerRepository;


    public Page<ContainerEntity> findAll(Pageable pageable) {
        return containerRepository.findAll(pageable);
    }

    public Iterable<ContainerEntity> findAll() {
        return containerRepository.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<ContainerEntity> getModel(int id) {
        return containerRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(ContainerEntity model) {
        containerRepository.save(model);
    }


    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        containerRepository.deleteById(id);
    }
}
