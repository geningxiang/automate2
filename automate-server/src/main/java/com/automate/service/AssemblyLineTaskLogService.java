package com.automate.service;

import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.repository.AssemblyLineTaskLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/6 19:21
 */
@Service
public class AssemblyLineTaskLogService {


    @Autowired
    private AssemblyLineTaskLogRepository assemblyLineTaskLogRepository;


    public List<AssemblyLineTaskLogEntity> findAllByAssemblyLineLogId(int assemblyLineLogId) {
        return assemblyLineTaskLogRepository.findAllByAssemblyLineLogIdOrderById(assemblyLineLogId);
    }

    public Iterable<AssemblyLineTaskLogEntity> findAll() {
        return assemblyLineTaskLogRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    /**
     * 查询对象
     **/
    public Optional<AssemblyLineTaskLogEntity> getModel(int id) {
        return assemblyLineTaskLogRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(AssemblyLineTaskLogEntity model) {
        assemblyLineTaskLogRepository.save(model);
    }

    public void saveAll(Iterable<AssemblyLineTaskLogEntity> assemblyLineTaskLogEntitys) {
        assemblyLineTaskLogRepository.saveAll(assemblyLineTaskLogEntitys);
    }

}
