package com.automate.service;

import com.automate.entity.AssemblyLineLogEntity;
import com.automate.repository.AssemblyLineLogRepository;
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
 * @date: 2019/2/6 19:16
 */
@Service
public class AssemblyLineLogService {

    @Autowired
    private AssemblyLineLogRepository assemblyLineLogRepository;

    public Page<AssemblyLineLogEntity> findAll(Pageable pageable) {
        return assemblyLineLogRepository.findAll(pageable);
    }


    public Iterable<AssemblyLineLogEntity> findAll() {
        return assemblyLineLogRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    /**
     * 查询对象
     **/
    public Optional<AssemblyLineLogEntity> getModel(int id) {
        return assemblyLineLogRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(AssemblyLineLogEntity model) {
        assemblyLineLogRepository.save(model);
    }


}
