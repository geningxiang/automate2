package com.automate.service;

import com.automate.entity.ContainerTypeEntity;
import com.automate.repository.ContainerTypeRepository;
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
 * @date: 2019/2/27 20:33
 */
@Service
public class ContainerTypeService {

    @Autowired
    private ContainerTypeRepository containerTypeRepository;


    public Page<ContainerTypeEntity> findAll(Pageable pageable) {
        return containerTypeRepository.findAll(pageable);
    }

    public Iterable<ContainerTypeEntity> findAll() {
        return containerTypeRepository.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<ContainerTypeEntity> getModel(int id) {
        return containerTypeRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(ContainerTypeEntity model) {
        containerTypeRepository.save(model);
    }


    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        containerTypeRepository.deleteById(id);
    }
}
