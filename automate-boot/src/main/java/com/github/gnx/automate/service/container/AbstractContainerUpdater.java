package com.github.gnx.automate.service.container;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.exec.IExecConnection;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/18 22:57
 */
public abstract class AbstractContainerUpdater implements IContainerUpdater {

    /**
     * 上传文件
     */
    @Override
    public void update(ContainerEntity containerEntity, ProductEntity productEntity, IExecConnection execConnection, IMsgListener msgListener) throws Exception {

        //1.上传更新文件
        msgListener.appendLine("== 开始上传更新文件 ==");
        this.upload(containerEntity, productEntity, execConnection, msgListener);

        //2.备份
        msgListener.appendLine("== 开始备份 ==");
        this.backup(containerEntity, execConnection, msgListener);

        //3.停止容器
        msgListener.appendLine("== 停止容器 ==");
        this.stop(containerEntity, execConnection, msgListener);

        //4.覆盖
        msgListener.appendLine("== 覆盖内容 ==");
        this.cover(containerEntity, execConnection, msgListener);

        //5.启动容器
        msgListener.appendLine("== 启动容器 ==");
        this.start(containerEntity, execConnection, msgListener);

    }

    @Override
    public void restart(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {
        this.stop(containerEntity, execConnection, execListener);

        this.start(containerEntity, execConnection, execListener);
    }

    /**
     * 上传文件
     */
    @Override
    public abstract void upload(ContainerEntity containerEntity, ProductEntity productEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;

    /**
     * 停止容器
     */
    @Override
    public abstract void stop(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;


    /**
     * 备份
     */
    @Override
    public abstract void backup(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;


    /**
     * 清理原目录、覆盖、清理上传文件
     */
    @Override
    public abstract void cover(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;


    /**
     * 启动
     */
    @Override
    public abstract void start(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception;


}
