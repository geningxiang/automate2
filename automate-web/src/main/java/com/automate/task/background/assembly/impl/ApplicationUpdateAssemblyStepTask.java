package com.automate.task.background.assembly.impl;

import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.ContainerEntity;
import com.automate.entity.ServerEntity;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import com.automate.service.ContainerService;
import com.automate.service.ServerService;
import com.automate.ssh.SSHSession;
import com.automate.ssh.SftpProgressMonitorImpl;
import com.automate.task.background.assembly.AbstractAssemblyStepTask;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/6 23:20
 */
public class ApplicationUpdateAssemblyStepTask extends AbstractAssemblyStepTask {

    private final String BASE_DIR = "${baseDir}";


    /**
     * 更新包或文件夹地址
     */
    private String path;

    /**
     * 容器ID
     */
    private Integer containerId;



    @Override
    public void valid() throws Exception {

    }

    @Override
    public boolean doInvoke() throws Exception {
        String path = getPath();

        Assert.hasText(path, "path is required");
        if(containerId == null || containerId <= 0){
            throw new IllegalArgumentException("containerId is required");
        }

        File file = new File(path);
        System.out.println(file.getAbsolutePath());

        if(!file.exists()){
            throw new IOException("文件不存在:" + file.getAbsolutePath());
        }


        if(file.isDirectory()){
            throw new IOException("暂不支持文件夹,等待后续完善");
        }

        int index = file.getName().lastIndexOf(".");
        if(index <= 0){
            throw new IOException("文件没有后缀?" + file.getName());
        }

        String suffix = file.getName().substring(index + 1).toLowerCase();
        if(!"war".equals(suffix)){
            throw new IllegalArgumentException("暂时只支持war后缀,等待后续完善");
        }

        ContainerService containerService = SpringContextUtil.getBean("containerService", ContainerService.class);
        ServerService serverService = SpringContextUtil.getBean("serverService", ServerService.class);

        Optional<ContainerEntity> containerEntity = containerService.getModel(containerId);
        if(!containerEntity.isPresent()){
            throw new IllegalArgumentException("未找到相应的容器:" + containerId);
        }

        Assert.hasText(containerEntity.get().getSourceDir(), "请先指定容器的代码文件夹");


        Optional<ServerEntity> serverEntity = serverService.getModel(containerEntity.get().getServerId());
        if(!serverEntity.isPresent()){
            throw new IllegalArgumentException("未找到相应的服务器:" + containerEntity.get().getServerId());
        }
        final String sourceDir = containerEntity.get().getSourceDir();
        SSHSession s = new SSHSession(serverEntity.get());

        s.doWork(sshConnection -> {



            String remoteDir = "/tmp/" + System.currentTimeMillis() + "/";
            sshConnection.uploadLocalFileToRemote(file.getAbsolutePath(), remoteDir, new SftpProgressMonitorImpl());

            StringBuilder cmd = new StringBuilder(1024);

            //TODO 关闭容器
            //TODO 备份

            //删除旧代码
            cmd.append("rm -rf ").append(sourceDir);

            cmd.append(" && mkdir -p -v ").append(sourceDir);

            cmd.append(" && unzip -o ").append(remoteDir).append(file.getName()).append(" -d ").append(sourceDir);

            //删除 压缩包
            cmd.append(" || rm -rf ").append(remoteDir);
            //TODO 启动容器

            ExecCommand execCommand = new ExecCommand(cmd.toString(), new ExecStreamPrintMonitor());
            sshConnection.exec(execCommand);


        });

        return true;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getContainerId() {
        return containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }
}
