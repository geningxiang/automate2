package com.automate.service;

import com.automate.repository.ServerRepository;
import com.automate.entity.ServerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/17 23:49
 */
@Service
public class ServerService {

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private EntityManager entityManager;

    public Iterable<ServerEntity> getList() {
        return serverRepository.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<ServerEntity> getModel(int id) {
        return serverRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(ServerEntity model) {
        serverRepository.save(model);
    }

    /**
     * 更新对象
     **/
    public void update(ServerEntity model) {
        serverRepository.save(model);
    }

    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        serverRepository.deleteById(id);
    }

}
