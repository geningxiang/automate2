package com.github.gnx.automate.service.container;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.exec.IExecConnection;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 17:26
 */
public class DefaultSSHContainerUpdater extends AbstractContainerUpdater {


    private String uploadFilePath;

    public DefaultSSHContainerUpdater(){

    }

    @Override
    public void upload(ContainerEntity containerEntity, ProductEntity productEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {
        File file = this.updateFileVerify(productEntity.getFilePath());
        execConnection.upload(file, containerEntity.getUploadDir(), false, execListener);
        this.uploadFilePath = containerEntity.getUploadDir() + "/" + file.getName();

    }

    @Override
    public void stop(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {

        execConnection.exec(containerEntity.getScriptStop(), execListener);

    }

    @Override
    public void backup(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {

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
        execConnection.exec(cmd.toString(), execListener);

    }

    @Override
    public void cover(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {

        if(StringUtils.isBlank(this.uploadFilePath)){
            throw new RuntimeException("请先上传更新包");
        }

        String sourceDir = containerEntity.getSourceDir();

        StringBuilder cmd = new StringBuilder(1024);
        //删除旧代码
        cmd.append("rm -rf ").append(sourceDir);

        cmd.append(" && mkdir -p -v ").append(sourceDir);
        //解压
        cmd.append(" && unzip -o ").append(uploadFilePath).append(" -d ").append(sourceDir);

        //暂时不删除 上传的更新包

        execConnection.exec(cmd.toString(), execListener);

    }

    @Override
    public void start(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {

        execConnection.exec(containerEntity.getScriptStart(), execListener);

    }

    /**
     * 检查产物文件
     * @param filePath
     * @return
     */
    private File updateFileVerify(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("更新文件不存在:" + file.getAbsolutePath());
        }

        if (file.isDirectory()) {
            throw new IllegalArgumentException("暂不支持文件夹,等待后续完善");
        }

        int index = file.getName().lastIndexOf(".");
        if (index <= 0) {
            throw new IllegalArgumentException("文件没有后缀?" + file.getName());
        }
        String suffix = file.getName().substring(index + 1).toLowerCase();
        if (!"war".equals(suffix) && !"zip".equals(suffix)) {
            throw new IllegalArgumentException("暂时只支持war、zip后缀,等待后续完善");
        }
        return file;
    }

}
