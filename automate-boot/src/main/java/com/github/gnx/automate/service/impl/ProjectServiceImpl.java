package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.common.thread.GlobalThreadPoolManager;
import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.event.IEventPublisher;
import com.github.gnx.automate.event.bean.EntityChangeEvent;
import com.github.gnx.automate.field.req.ReqProjectCreateField;
import com.github.gnx.automate.repository.ProjectRepository;
import com.github.gnx.automate.service.IProjectService;
import com.github.gnx.automate.vcs.VcsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    private final ProjectRepository projectRepository;

    private final VcsHelper vcsHelper;

    private final IEventPublisher eventPublisher;

    public ProjectServiceImpl(VcsHelper vcsHelper, ProjectRepository projectRepository, IEventPublisher eventPublisher) {
        this.vcsHelper = vcsHelper;
        this.projectRepository = projectRepository;
        this.eventPublisher = eventPublisher;
    }

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
    public Optional<ProjectEntity> findById(int id) {
        return projectRepository.findById(id);
    }


    /**
     *  创建项目
     * @param model
     * @param userId
     */
    @Override
    public ProjectEntity create(ReqProjectCreateField model, int userId) {
        ProjectEntity local = projectRepository.getFirstByNameOrVcsUrl(model.getName(), model.getVcsUrl());
        if (local != null) {
            throw new IllegalArgumentException("项目名称或版本控制地址已存在, projectId:" + local.getId());
        }
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setType(model.getType().ordinal());
        projectEntity.setName(model.getName());
        projectEntity.setDescription(model.getDescription());
        projectEntity.setVcsType(model.getVcsType().ordinal());
        projectEntity.setVcsUrl(model.getVcsUrl());
        projectEntity.setVcsUserName(model.getVcsUserName());
        projectEntity.setVcsPassWord(model.getVcsPassWord());
        projectEntity.setCompileType(model.getCompileType().ordinal());
        projectEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        projectEntity.setUserId(userId);
        projectEntity.setStatus(ProjectEntity.Status.ACTIVATE);
        this.save(projectEntity);

        //TODO 做成异步事件
        GlobalThreadPoolManager.getInstance().execute(() -> {
            //VCS 更新
            try {
                vcsHelper.update(projectEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return projectEntity;
    }


    @Override
    public void save(ProjectEntity projectEntity){
        this.projectRepository.save(projectEntity);

        eventPublisher.publishEvent(new EntityChangeEvent(projectEntity));
    }

}
