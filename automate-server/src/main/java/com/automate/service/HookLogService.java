package com.automate.service;

import com.automate.entity.HookLogEntity;
import com.automate.repository.HookLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:26
 */
@Service
public class HookLogService {

    @Autowired
    private HookLogRepository hookLogRepository;

    @Autowired
    private EntityManager entityManager;

    public Page<HookLogEntity> findAll(Pageable pageable) {
        return hookLogRepository.findAll(pageable);
    }

    public Iterable<HookLogEntity> findAll() {
        return hookLogRepository.findAll(Sort.by("id").descending());
    }

    /**
     * 查询对象
     **/
    public Optional<HookLogEntity> getModel(int id) {
        return hookLogRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(HookLogEntity model) {
        if(model.getCreateTime() == null) {
            model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        hookLogRepository.save(model);
    }

    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        hookLogRepository.deleteById(id);
    }
}
