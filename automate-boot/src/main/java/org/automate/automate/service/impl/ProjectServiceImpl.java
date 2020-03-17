package org.automate.automate.service.impl;

import org.automate.automate.entity.ProjectEntity;
import org.automate.automate.repository.ProjectRepository;
import org.automate.automate.service.IProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:26
 */
@Service
public class ProjectServiceImpl implements IProjectService {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Resource
    private ProjectRepository projectRepository;

    @Override
    public ProjectEntity getFirstByVcsUrl(String vcsUrl) {
        return projectRepository.getFirstByVcsUrl(vcsUrl);
    }

    @Override
    public Iterable<ProjectEntity> findAll() {
        return projectRepository.findAll(Sort.by("id"));
    }

    @Override
    public Map<Integer, ProjectEntity> findAllWidthMap() {
        Iterable<ProjectEntity> list = this.findAll();
        Map<Integer, ProjectEntity> map = new HashMap<>(256);
        for (ProjectEntity projectEntity : list) {
            map.put(projectEntity.getId(), projectEntity);
        }
        return map;
    }

    /**
     * 查询对象
     **/
    @Override
    public Optional<ProjectEntity> getModel(int id) {
        return projectRepository.findById(id);
    }


    /**
     * 添加对象
     **/
    @Override
    public void save(ProjectEntity model) {
        if (model.getCreateTime() == null) {
            model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        this.projectRepository.save(model);
    }

}
