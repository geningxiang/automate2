package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.common.file.FileInfo;
import com.github.gnx.automate.common.file.FileListSha256Util;
import com.github.gnx.automate.common.thread.GlobalThreadPoolManager;
import com.github.gnx.automate.entity.*;
import com.github.gnx.automate.event.IEventPublisher;
import com.github.gnx.automate.event.bean.EntityChangeEvent;
import com.github.gnx.automate.exec.DefaultMsgListener;
import com.github.gnx.automate.exec.ExecWorker;
import com.github.gnx.automate.exec.IExecConnection;
import com.github.gnx.automate.exec.ssh.SSHConnection;
import com.github.gnx.automate.exec.ssh.SSHExecTemplate;
import com.github.gnx.automate.exec.ssh.SSHUtil;
import com.github.gnx.automate.repository.ContainerRepository;
import com.github.gnx.automate.repository.ContainerUpdateLogRepository;
import com.github.gnx.automate.repository.ProductRepository;
import com.github.gnx.automate.repository.ServerRepository;
import com.github.gnx.automate.service.IContainerService;
import com.github.gnx.automate.service.IFileListShaService;
import com.github.gnx.automate.service.container.DefaultSSHContainerUpdater;
import com.github.gnx.automate.service.container.IContainerUpdater;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:51
 */
@Service
public class ContainerServiceImpl implements IContainerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ContainerRepository containerRepository;

    private final ProductRepository productRepository;

    private final ServerRepository serverRepository;

    private final ContainerUpdateLogRepository containerUpdateLogRepository;

    private final IFileListShaService fileListShaService;

    private final IEventPublisher eventPublisher;

    public ContainerServiceImpl(ContainerRepository containerRepository, ProductRepository productRepository, ServerRepository serverRepository, ContainerUpdateLogRepository containerUpdateLogRepository, IFileListShaService fileListShaService, IEventPublisher eventPublisher) {
        this.containerRepository = containerRepository;
        this.productRepository = productRepository;
        this.serverRepository = serverRepository;
        this.containerUpdateLogRepository = containerUpdateLogRepository;
        this.fileListShaService = fileListShaService;
        this.eventPublisher = eventPublisher;
    }


    @Override
    public Optional<ContainerEntity> findById(int id) {
        return this.containerRepository.findById(id);
    }


    @Override
    public Iterable<ContainerEntity> findAll() {
        return this.containerRepository.findAll();
    }

    @Override
    public List<ContainerEntity> getAllByProjectIdOrderById(int projectId) {
        return containerRepository.getAllByProjectIdOrderById(projectId);
    }

    @Override
    public void save(ContainerEntity containerEntity){
        this.containerRepository.save(containerEntity);

        this.eventPublisher.publishEvent(new EntityChangeEvent(containerEntity));
    }

    /**
     * 更新容器
     * @param productId 产物ID
     * @param containerId 容器ID
     * @throws Exception
     */
    @Override
    public void updateContainer(int productId, int containerId) throws Exception {

        logger.info("开始更新容器, 项目ID: {}, 容器ID: {}", productId, containerId);

        ProductEntity productEntity = productRepository.findById(productId).get();
        ContainerEntity containerEntity = containerRepository.findById(containerId).get();
        ServerEntity serverEntity = serverRepository.findById(containerEntity.getServerId()).get();

        ContainerUpdateLogEntity log = new ContainerUpdateLogEntity();
        log.setContainerId(containerId);
        log.setProjectId(productEntity.getProjectId());
        log.setProductId(productEntity.getId());
        log.setServerId(serverEntity.getId());
        log.setApplyId(0);
        log.setUserId(0);
        log.setType(0);
        log.setCreateTime(new Timestamp(System.currentTimeMillis()));
        log.setStatus(AssemblyLineLogEntity.Status.INIT);
        this.containerUpdateLogRepository.save(log);

        //源代码文件夹
        final String sourceDir = containerEntity.getSourceDir();
        SSHExecTemplate sshExecTemplate = new SSHExecTemplate(serverEntity.getSshHost(), serverEntity.getSshPort(), serverEntity.getSshUser(), serverEntity.getSshPwd());

        List<FileInfo> afterFileSha256List = sshExecTemplate.execute(new ExecWorker<List<FileInfo>>() {
            @Override
            public List<FileInfo> doWork(IExecConnection execConnection) throws Exception {

                SSHConnection sshConnection = (SSHConnection) execConnection;

                log.appendLine("已建立ssh连接: " + serverEntity.getSshHost() + ":" + serverEntity.getSshPort());

                //当前源码的filelist
                List<FileInfo> fileSha256List = SSHUtil.sha256sum(sshConnection, sourceDir);

                String fileList = FileListSha256Util.parseToString(fileSha256List);
                String beforeSha256 = DigestUtils.sha256Hex(fileList);
                log.setBeforeSha256(beforeSha256);

                log.appendLine("更新前sha256: " + beforeSha256);

                IContainerUpdater containerUpdater = new DefaultSSHContainerUpdater();

                containerUpdater.update(containerEntity, productEntity, sshConnection, log);

                List<FileInfo> afterFileSha256List = SSHUtil.sha256sum(sshConnection, sourceDir);


                //更新后的文件sha256
                return afterFileSha256List;
            }
        });

        String afterFileList = FileListSha256Util.parseToString(afterFileSha256List);
        String afterSha256 = DigestUtils.sha256Hex(afterFileList);

        log.appendLine("更新后sha256: " + afterSha256);


        log.setDoneTime(new Timestamp(System.currentTimeMillis()));
        log.setStatus(AssemblyLineLogEntity.Status.SUCCESS);

        log.setAfterSha256(afterSha256);

        this.containerUpdateLogRepository.save(log);

        //保存 sha256 fileList关系
        this.fileListShaService.save(afterSha256, afterFileList);

    }

    @Override
    public boolean check(int containerId) throws Exception {
        Optional<ContainerEntity> containerOptional = this.containerRepository.findById(containerId);
        Optional<ServerEntity> serverOptional = this.serverRepository.findById(containerOptional.get().getServerId());
        ServerEntity serverEntity = serverOptional.get();
        SSHExecTemplate sshExecTemplate = new SSHExecTemplate(serverEntity.getSshHost(), serverEntity.getSshPort(), serverEntity.getSshUser(), serverEntity.getSshPwd());
        return sshExecTemplate.execute(new ExecWorker<Boolean>() {
            @Override
            public Boolean doWork(IExecConnection execConnection) throws Exception {
                SSHConnection sshConnection = (SSHConnection) execConnection;
                return new DefaultSSHContainerUpdater().check(containerOptional.get(), sshConnection, new DefaultMsgListener());
            }
        });
    }

    @Override
    public void start(int containerId) throws Exception {
        Optional<ContainerEntity> containerOptional = this.containerRepository.findById(containerId);
        Optional<ServerEntity> serverOptional = this.serverRepository.findById(containerOptional.get().getServerId());
        ServerEntity serverEntity = serverOptional.get();
        SSHExecTemplate sshExecTemplate = new SSHExecTemplate(serverEntity.getSshHost(), serverEntity.getSshPort(), serverEntity.getSshUser(), serverEntity.getSshPwd());
        sshExecTemplate.execute(new ExecWorker<Object>() {
            @Override
            public Object doWork(IExecConnection execConnection) throws Exception {
                SSHConnection sshConnection = (SSHConnection) execConnection;
                new DefaultSSHContainerUpdater().start(containerOptional.get(), sshConnection, new DefaultMsgListener());
                return null;
            }
        });
    }

    @Override
    public void stop(int containerId) throws Exception {
        Optional<ContainerEntity> containerOptional = this.containerRepository.findById(containerId);
        Optional<ServerEntity> serverOptional = this.serverRepository.findById(containerOptional.get().getServerId());
        ServerEntity serverEntity = serverOptional.get();
        SSHExecTemplate sshExecTemplate = new SSHExecTemplate(serverEntity.getSshHost(), serverEntity.getSshPort(), serverEntity.getSshUser(), serverEntity.getSshPwd());
        sshExecTemplate.execute(new ExecWorker<Object>() {
            @Override
            public Object doWork(IExecConnection execConnection) throws Exception {
                SSHConnection sshConnection = (SSHConnection) execConnection;
                new DefaultSSHContainerUpdater().stop(containerOptional.get(), sshConnection, new DefaultMsgListener());
                return null;
            }
        });
    }

    @Override
    public Future<List<FileInfo>> getFileInfoList(int containerId) {
        final ContainerEntity container = this.containerRepository.findById(containerId).get();

        final ServerEntity serverEntity = this.serverRepository.findById(container.getServerId()).get();

        return GlobalThreadPoolManager.getInstance().submit(() -> {
            SSHExecTemplate sshExecTemplate = new SSHExecTemplate(serverEntity.getSshHost(), serverEntity.getSshPort(), serverEntity.getSshUser(), serverEntity.getSshPwd());
            return sshExecTemplate.execute(new ExecWorker<List<FileInfo>>() {
                @Override
                public List<FileInfo> doWork(IExecConnection execConnection) throws Exception {
                    return SSHUtil.sha256sum((SSHConnection) execConnection, container.getSourceDir());
                }
            });
        });
    }


    private void backup(ContainerEntity containerEntity, SSHConnection sshConnection, String beforeSha256, IMsgListener msgLineReader) throws Exception {
        if (StringUtils.isBlank(containerEntity.getBackupDir())) {
            msgLineReader.append("[WARN]当前应用未设置备份文件夹");
            return;
        }

        msgLineReader.append("====== 开始备份 ======");
        StringBuilder backUpFilePath = new StringBuilder(256);
        backUpFilePath.append(containerEntity.getBackupDir());
        if (!containerEntity.getBackupDir().endsWith("/")) {
            backUpFilePath.append("/");
        }
        backUpFilePath.append(FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(System.currentTimeMillis())).append(".tar.gz");

        logger.info("{} ==> {}", containerEntity.getSourceDir(), backUpFilePath.toString());

        msgLineReader.append(containerEntity.getSourceDir() + " ==> " + backUpFilePath.toString());

        //原文件夹 压缩
//        sshConnection.tar(containerEntity.getSourceDir(), backUpFilePath.toString(), msgLineReader);

        msgLineReader.append("====== 备份结束 ======");

        //保存应用备份表
//        ContainerBackupEntity applicationBackupEntity = new ContainerBackupEntity();
//        applicationBackupEntity.setApplicationId(containerEntity.getId());
//        applicationBackupEntity.setFilePath(backUpFilePath.toString());
//        applicationBackupEntity.setSha256(beforeSha256);
//        applicationBackupEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        applicationBackupRepository.save(applicationBackupEntity);

    }


}
