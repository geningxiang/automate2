package com.github.gnx.automate.service;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.entity.ContainerEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:50
 */
public interface IContainerService {
    List<ContainerEntity> getAllByProjectIdOrderById(int projectId);

    void update(int productId, int containerId, IMsgListener msgLineReader) throws Exception;
}
