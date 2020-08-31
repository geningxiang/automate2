package com.github.gnx.automate.cache;

import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.entity.ServerEntity;
import com.github.gnx.automate.vo.response.ContainerVO;
import com.github.gnx.automate.vo.response.ProductVO;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/31 20:39
 */
public interface IEntityCache {
    ProjectEntity getProjectById(int projectId);

    String getProjectName(int projectId);

    ServerEntity getServerById(int serverId);

    String getServerName(int serverId);

    ContainerEntity getContainerById(int containerId);

    ContainerVO parse(ContainerEntity containerEntity);

    ProductVO parse(ProductEntity productEntity);
}
