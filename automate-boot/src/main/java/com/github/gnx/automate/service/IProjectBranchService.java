package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.ProjectBranchEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 21:57
 */
public interface IProjectBranchService {


    List<ProjectBranchEntity> getList(int projectId);
}
