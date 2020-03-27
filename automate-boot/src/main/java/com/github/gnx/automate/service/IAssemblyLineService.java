package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.AssemblyLineEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 21:23
 */
public interface IAssemblyLineService {
    List<AssemblyLineEntity> getAllByProjectIdOrderById(int projectId);
}
