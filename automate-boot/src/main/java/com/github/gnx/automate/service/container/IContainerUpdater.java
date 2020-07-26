package com.github.gnx.automate.service.container;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.exec.IExecConnection;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/18 22:48
 */
public interface IContainerUpdater {



    /**
     * 上传文件
     */
    void upload(ContainerEntity containerEntity, ProductEntity productEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;

    /**
     * 停止容器
     */
    void stop(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;


    /**
     * 备份
     */
    void backup(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;


    /**
     * 清理原目录、覆盖、清理上传文件
     */
    void cover(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;


    /**
     * 启动
     */
    void start(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;


    void restart(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;


    /**
     * 更新
     */
    void update(ContainerEntity containerEntity, ProductEntity productEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;



}
