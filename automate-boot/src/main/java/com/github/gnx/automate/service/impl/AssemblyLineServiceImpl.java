package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.repository.AssemblyLineRepository;
import com.github.gnx.automate.service.IAssemblyLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 21:23
 */
@Service
public class AssemblyLineServiceImpl implements IAssemblyLineService {

    @Autowired
    private AssemblyLineRepository assemblyLineRepository;

    @Override
    public List<AssemblyLineEntity> getAllByProjectIdOrderById(int projectId) {
        return this.assemblyLineRepository.getAllByProjectIdOrderById(projectId);
    }

}
