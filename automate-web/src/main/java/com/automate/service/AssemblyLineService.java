package com.automate.service;

import com.automate.entity.AssemblyLineEntity;
import com.automate.repository.AssemblyLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/4 16:23
 */
@Service
public class AssemblyLineService {

    @Autowired
    private AssemblyLineRepository assemblyLineRepository;


    public Iterable<AssemblyLineEntity> findAll() {
        return assemblyLineRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    /**
     * 查询对象
     **/
    public Optional<AssemblyLineEntity> getModel(int id) {
        return assemblyLineRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(AssemblyLineEntity model) {
        assemblyLineRepository.save(model);
    }


}
