package com.github.gnx.automate.service.container;

import com.github.gnx.automate.common.IExecListener;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.exec.IExecConnection;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/18 22:57
 */
public abstract class AbstractContainerUpdater {


    private IExecConnection execConnection;


    /**
     * 上传文件
     */
    public abstract void upload(ContainerEntity containerEntity, ProductEntity productEntity, IExecConnection execConnection, IExecListener execListener);

    /**
     * 停止容器
     */
    public abstract void stop(ContainerEntity containerEntity, IExecConnection execConnection, IExecListener execListener);


    /**
     * 备份
     */
    public abstract void backup(ContainerEntity containerEntity, IExecConnection execConnection, IExecListener execListener);


    /**
     * 清理原目录、覆盖、清理上传文件
     */
    public abstract void cover(ContainerEntity containerEntity, IExecConnection execConnection, IExecListener execListener);


    /**
     * 启动
     */
    public abstract void start(ContainerEntity containerEntity, IExecConnection execConnection, IExecListener execListener);


}
