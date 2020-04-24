package com.github.gnx.automate.service.container;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.exec.IExecConnection;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 17:26
 */
public class DefaultSSHContainerUpdater extends AbstractContainerUpdater {


    private String uploadFilePath;

    public DefaultSSHContainerUpdater(IExecConnection execConnection){

    }

    @Override
    public void upload(ContainerEntity containerEntity, ProductEntity productEntity, IExecConnection execConnection, IMsgListener execListener) {

//        execConnection.upload(productEntity.getFilePath(), containerEntity.getUploadDir(), null);

    }

    @Override
    public void stop(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) {

//        execConnection.execCmd(containerEntity.getScriptStop(), execListener);

    }

    @Override
    public void backup(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) {

        StringBuilder backUpFilePath = new StringBuilder(256);
        backUpFilePath.append(containerEntity.getBackupDir());
        if (!containerEntity.getBackupDir().endsWith("/")) {
            backUpFilePath.append("/");
        }
        backUpFilePath.append(FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(System.currentTimeMillis())).append(".tar.gz");


        StringBuilder cmd = new StringBuilder(512);
        cmd.append("cd ");
        cmd.append(containerEntity.getSourceDir());
        cmd.append(" && tar -zcvf ");

        cmd.append(backUpFilePath.toString());
        cmd.append(" ./");

//        execConnection.execCmd(cmd.toString(), execListener);

    }

    @Override
    public void cover(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) {

        String sourceDir = containerEntity.getSourceDir();

        StringBuilder cmd = new StringBuilder(1024);
        //删除旧代码
        cmd.append("rm -rf ").append(sourceDir);

        cmd.append(" && mkdir -p -v ").append(sourceDir);
        //解压
        cmd.append(" && unzip -o ").append(uploadFilePath).append(" -d ").append(sourceDir);

        //暂时不删除 上传的更新包

    }

    @Override
    public void start(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) {

//        execConnection.execCmd(containerEntity.getScriptStart(), execListener);

    }
}
