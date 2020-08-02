package com.github.gnx.automate.service;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.common.file.FileInfo;
import com.github.gnx.automate.entity.ContainerEntity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:50
 */
public interface IContainerService {


    Optional<ContainerEntity> findById(int id);

    Iterable<ContainerEntity> findAll();

    List<ContainerEntity> getAllByProjectIdOrderById(int projectId);

    void update(int productId, int containerId, IMsgListener msgLineReader) throws Exception;

    /**
     * 检查容器状态
     * @param containerId
     * @return true(running)  false(not run)
     * @throws Exception
     */
    boolean check(int containerId) throws Exception;

    void start(int containerId) throws Exception;

    void stop(int containerId) throws Exception;

    Future<List<FileInfo>> getFileInfoList(int containerId);
}
