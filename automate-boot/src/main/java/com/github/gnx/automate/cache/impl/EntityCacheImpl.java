package com.github.gnx.automate.cache.impl;

import com.github.gnx.automate.cache.IEntityCache;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.entity.ServerEntity;
import com.github.gnx.automate.event.bean.EntityChangeEvent;
import com.github.gnx.automate.service.IContainerService;
import com.github.gnx.automate.service.IProjectService;
import com.github.gnx.automate.service.IServerService;
import com.github.gnx.automate.vo.response.ContainerVO;
import com.github.gnx.automate.vo.response.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/31 20:39
 */
@Service
public class EntityCacheImpl implements IEntityCache {

    private final IProjectService projectService;

    private final IServerService serverService;

    private final IContainerService containerService;

    private final Map<Integer, ProjectEntity> projectCache = new ConcurrentHashMap(128);

    private final Map<Integer, ServerEntity> serverCache = new ConcurrentHashMap(64);

    private final Map<Integer, ContainerEntity> containerCache = new ConcurrentHashMap(128);


    public EntityCacheImpl(IProjectService projectService, IServerService serverService, IContainerService containerService) {
        this.projectService = projectService;
        this.serverService = serverService;
        this.containerService = containerService;

        init();
    }

    private void init() {
        this.projectService.findAll().forEach(projectEntity -> {
            projectCache.put(projectEntity.getId(), projectEntity);
        });

        this.serverService.findAll().forEach(serverEntity -> {
            serverCache.put(serverEntity.getId(), serverEntity);
        });

        this.containerService.findAll().forEach(containerEntity -> {
            containerCache.put(containerEntity.getId(), containerEntity);
        });
    }


    @Override
    public ProjectEntity getProjectById(int projectId) {
        return this.projectCache.get(projectId);
    }

    @Override
    public String getProjectName(int projectId) {
        ProjectEntity projectEntity = this.getProjectById(projectId);
        return projectEntity != null ? projectEntity.getName() : "[" + projectId + "]";
    }


    @Override
    public ServerEntity getServerById(int serverId) {
        return this.serverCache.get(serverId);
    }

    @Override
    public String getServerName(int serverId) {
        ServerEntity serverEntity = this.getServerById(serverId);
        return serverEntity != null ? serverEntity.getName() : "[" + serverId + "]";
    }


    @Override
    public ContainerEntity getContainerById(int containerId) {
        return this.containerCache.get(containerId);
    }

    public String getContainerName(int containerId) {
        ContainerEntity containerEntity = this.getContainerById(containerId);
        return containerEntity != null ? containerEntity.getName() : "[" + containerId + "]";
    }

    @EventListener
    @Async
    public void onEntityChange(EntityChangeEvent entityChangeEvent) {
        if (entityChangeEvent == null) {
            return;
        }
        Object data = entityChangeEvent.getData();
        if (data == null) {
            return;
        }

        if (data instanceof ProjectEntity) {
            projectCache.put(((ProjectEntity) data).getId(), (ProjectEntity) data);
        } else if (data instanceof ServerEntity) {
            serverCache.put(((ServerEntity) data).getId(), (ServerEntity) data);
        } else if (data instanceof ContainerEntity) {
            containerCache.put(((ContainerEntity) data).getId(), (ContainerEntity) data);
        }
    }

    @Override
    public ContainerVO parse(ContainerEntity containerEntity) {
        ContainerVO vo = new ContainerVO();
        BeanUtils.copyProperties(containerEntity, vo);
        vo.setServerName(this.getServerName(vo.getServerId()));
        vo.setProjectName(this.getProjectName(vo.getProjectId()));
        return vo;
    }

    @Override
    public ProductVO parse(ProductEntity productEntity) {
        ProductVO vo = new ProductVO();
        BeanUtils.copyProperties(productEntity, vo);
        vo.setProjectName(this.getProjectName(vo.getProjectId()));
        return vo;
    }
}
