package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.field.req.ReqProjectCreateField;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/18 0:14
 */
public interface IProjectService extends IBaseEntityService<ProjectEntity> {
    ProjectEntity getFirstByVcsUrl(String vcsUrl);

    Map<Integer, ProjectEntity> findAllWidthMap();

    ProjectEntity create(ReqProjectCreateField model, int userId);
}
