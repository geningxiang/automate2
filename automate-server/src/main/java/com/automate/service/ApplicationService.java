package com.automate.service;

import com.automate.entity.ApplicationEntity;
import com.automate.entity.ProjectPackageEntity;
import com.automate.entity.ServerEntity;
import com.automate.exec.ExecCommand;
import com.automate.repository.ApplicationRepository;
import com.automate.ssh.SSHConnection;
import com.automate.ssh.SSHSession;
import com.automate.ssh.SSHUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/27 20:32
 */
@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;


    public void doUpdate(ProjectPackageEntity projectPackageEntity, ApplicationEntity applicationEntity) {

    }

    public Page<ApplicationEntity> findAll(Pageable pageable) {
        return applicationRepository.findAll(pageable);
    }

    public Iterable<ApplicationEntity> findAll() {
        return applicationRepository.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<ApplicationEntity> findById(int id) {
        return applicationRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(ApplicationEntity model) {
        applicationRepository.save(model);
    }


    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        applicationRepository.deleteById(id);
    }


    public static ExecCommand containerStart(ApplicationEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getScriptStart(), "当前未配置容器启动脚本");
        ExecCommand execCommand = new ExecCommand(containerEntity.getScriptStart());
        getSSHSession(containerEntity.getServerId()).doWork(sshConnection -> sshConnection.exec(execCommand));
        return execCommand;
    }

    public static ExecCommand containerStart(SSHConnection sshConnection, ApplicationEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getScriptStart(), "当前未配置容器启动脚本");
        ExecCommand execCommand = new ExecCommand(containerEntity.getScriptStart());
        sshConnection.exec(execCommand);
        return execCommand;
    }

    public static ExecCommand containerStop(ApplicationEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getScriptStop(), "当前未配置容器停止脚本");
        ExecCommand execCommand = new ExecCommand(containerEntity.getScriptStop());
        getSSHSession(containerEntity.getServerId()).doWork(sshConnection -> sshConnection.exec(execCommand));
        return execCommand;
    }

    public static ExecCommand containerStop(SSHConnection sshConnection, ApplicationEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getScriptStop(), "当前未配置容器停止脚本");
        ExecCommand execCommand = new ExecCommand(containerEntity.getScriptStop());
        sshConnection.exec(execCommand);
        return execCommand;
    }

    public static ExecCommand containerCheck(ApplicationEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getScriptCheck(), "当前未配置容器检查脚本");
        ExecCommand execCommand = new ExecCommand(containerEntity.getScriptCheck());
        getSSHSession(containerEntity.getServerId()).doWork(sshConnection -> sshConnection.exec(execCommand));
        return execCommand;
    }

    public static ExecCommand containerCheck(SSHConnection sshConnection, ApplicationEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getScriptCheck(), "当前未配置容器检查脚本");
        ExecCommand execCommand = new ExecCommand(containerEntity.getScriptCheck());
        sshConnection.exec(execCommand);
        return execCommand;
    }

    public static List<String[]> fileSha256List(ApplicationEntity applicationEntity) throws Exception {
        Assert.hasText(applicationEntity.getSourceDir(), "当前未配置容器内容文件夹");
        ServerEntity serverEntity = ServerService.getModelByCache(applicationEntity.getServerId());
        Assert.notNull(serverEntity, "未找到相应的服务器");
        SSHSession sshSession = new SSHSession(serverEntity);
        return SSHUtil.sha256sum(sshSession, applicationEntity.getSourceDir());
    }




    private static SSHSession getSSHSession(int serverId) {
        ServerEntity serverEntity = ServerService.getModelByCache(serverId);
        Assert.notNull(serverEntity, "未找到相应的服务器");
        return new SSHSession(serverEntity);
    }
}
