package com.automate.service;

import com.automate.entity.ContainerEntity;
import com.automate.entity.ServerEntity;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import com.automate.repository.ContainerRepository;
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
public class ContainerService {

    @Autowired
    private ContainerRepository containerRepository;


    public Page<ContainerEntity> findAll(Pageable pageable) {
        return containerRepository.findAll(pageable);
    }

    public Iterable<ContainerEntity> findAll() {
        return containerRepository.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<ContainerEntity> getModel(int id) {
        return containerRepository.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(ContainerEntity model) {
        containerRepository.save(model);
    }


    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        containerRepository.deleteById(id);
    }


    public static ExecCommand containerStart(ContainerEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getScriptStart(), "当前未配置容器启动脚本");
        return containerOperation(containerEntity.getServerId(), containerEntity.getScriptStart());
    }

    public static ExecCommand containerStop(ContainerEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getScriptStop(), "当前未配置容器停止脚本");
        return containerOperation(containerEntity.getServerId(), containerEntity.getScriptStop());
    }

    public static ExecCommand containerCheck(ContainerEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getScriptCheck(), "当前未配置容器检查脚本");
        return containerOperation(containerEntity.getServerId(), containerEntity.getScriptCheck());
    }

    public static List<String[]> fileMd5List(ContainerEntity containerEntity) throws Exception {
        Assert.hasText(containerEntity.getSourceDir(), "当前未配置容器内容文件夹");
        ServerEntity serverEntity = ServerService.getModelByCache(containerEntity.getServerId());
        Assert.notNull(serverEntity, "未找到相应的服务器");
        SSHSession sshSession = new SSHSession(serverEntity);
        return SSHUtil.md5sum(sshSession, containerEntity.getSourceDir());

    }

    public static ExecCommand containerOperation(Integer serverId, String script) throws Exception {
        ServerEntity serverEntity = ServerService.getModelByCache(serverId);
        Assert.notNull(serverEntity, "未找到相应的服务器");
        SSHSession sshSession = new SSHSession(serverEntity);
        ExecCommand execCommand = new ExecCommand(script);
        execCommand.setCmdStreamMonitor(new ExecStreamPrintMonitor());
        sshSession.doWork(sshConnection -> sshConnection.exec(execCommand));
        return execCommand;
    }
}
