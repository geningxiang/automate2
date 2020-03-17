package org.automate.automate.service;

import org.automate.automate.entity.ProjectEntity;

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
}
