package com.github.gnx.automate.service.container;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.exec.DefaultMsgListener;
import com.github.gnx.automate.exec.IExecConnection;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.File;
import java.util.List;

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
    public boolean check(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {
        int exitStatus = execConnection.exec(containerEntity.getScriptCheck(), execListener);
        // 0 运行中  1 未运行
        return exitStatus == 0;
    }

    @Override
    public void upload(ContainerEntity containerEntity, ProductEntity productEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {
        File file = this.updateFileVerify(productEntity.getFilePath());
        execConnection.upload(file, containerEntity.getUploadDir(), false, execListener);
        this.uploadFilePath = containerEntity.getUploadDir() + "/" + file.getName();
    }

    @Override
    public void stop(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {
        DefaultMsgListener defaultMsgListener = new DefaultMsgListener(execListener, false);
        int exit = execConnection.exec(containerEntity.getScriptStop(), defaultMsgListener);
        if(exit == 0){
            //停止成功
        } else if(exit == 2){
            //本来就没运行
            throw new RuntimeException("server is not run");
        } else {
            throw new RuntimeException(defaultMsgListener.getContent());
        }
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
        cmd.append(" && tar -zcf ");

        cmd.append(backUpFilePath.toString());
        cmd.append(" ./");
        execConnection.exec(cmd.toString(), execListener);

        execListener.appendLine("备份后文件: " + backUpFilePath.toString());
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
        //解压  -q 执行时不显示任何信息。
        cmd.append(" && unzip -o -q ").append(uploadFilePath).append(" -d ").append(sourceDir);

        //暂时不删除 上传的更新包
        execConnection.exec(cmd.toString(), execListener);
    }

    @Override
    public void start(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {
        DefaultMsgListener defaultMsgListener = new DefaultMsgListener(execListener, false);
        int exit = execConnection.exec(containerEntity.getScriptStart(), defaultMsgListener);
        if(exit == 0){

        } else if(exit == 2){
            //本来就是已运行了
            throw new RuntimeException("server is already running");
        } else {
            throw new RuntimeException(defaultMsgListener.getContent());
        }
    }


    public List sha256(ContainerEntity containerEntity, IExecConnection execConnection, IMsgListener execListener) throws Exception {

        return null;

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
