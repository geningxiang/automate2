package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.common.utils.FileListSha256Util;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.entity.ServerEntity;
import com.github.gnx.automate.exec.DefaultMsgListener;
import com.github.gnx.automate.exec.ExecWorker;
import com.github.gnx.automate.exec.IExecConnection;
import com.github.gnx.automate.exec.ssh.SSHConnection;
import com.github.gnx.automate.exec.ssh.SSHExecTemplate;
import com.github.gnx.automate.exec.ssh.SSHUtil;
import com.github.gnx.automate.repository.ContainerRepository;
import com.github.gnx.automate.repository.ProductRepository;
import com.github.gnx.automate.repository.ServerRepository;
import com.github.gnx.automate.service.IContainerService;
import com.github.gnx.automate.service.container.DefaultSSHContainerUpdater;
import com.github.gnx.automate.service.container.IContainerUpdater;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 23:51
 */
@Service
public class ContainerServiceImpl implements IContainerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ServerRepository serverRepository;


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

    /**
     * 更新容器
     * @param productId 产物ID
     * @param containerId 容器ID
     * @param msgLineReader
     * @throws Exception
     */
    @Override
    public void update(int productId, int containerId, IMsgListener msgLineReader) throws Exception {

        ProductEntity productEntity = productRepository.findById(productId).get();
        ContainerEntity containerEntity = containerRepository.findById(containerId).get();
        ServerEntity serverEntity = serverRepository.findById(containerEntity.getServerId()).get();


        //源代码文件夹
        final String sourceDir = containerEntity.getSourceDir();


        SSHExecTemplate sshExecTemplate = new SSHExecTemplate(serverEntity.getSshHost(), serverEntity.getSshPort(), serverEntity.getSshUser(), serverEntity.getSshPwd());


        sshExecTemplate.execute(new ExecWorker<Object>() {
            @Override
            public Object doWork(IExecConnection execConnection) throws Exception {
                SSHConnection sshConnection = (SSHConnection) execConnection;

                msgLineReader.appendLine("已建立ssh连接: " + serverEntity.getSshHost() + ":" + serverEntity.getSshPort());

                //当前源码的filelist
                List<String[]> fileSha256List = SSHUtil.sha256sum(sshConnection, sourceDir);

                String fileList = FileListSha256Util.parseToFileListByArray(fileSha256List);
                String beforeSha256 = DigestUtils.sha256Hex(fileList);

                msgLineReader.appendLine("更新前sha256: " + beforeSha256);


                IContainerUpdater containerUpdater = new DefaultSSHContainerUpdater();

                containerUpdater.update(containerEntity, productEntity, sshConnection, msgLineReader);


                //更新后的文件sha256
                List<String[]> afterFileSha256List = SSHUtil.sha256sum(sshConnection, sourceDir);
                String afterFileList = FileListSha256Util.parseToFileListByArray(afterFileSha256List);
                String afterSha256 = DigestUtils.sha256Hex(afterFileList);

                msgLineReader.appendLine("更新后sha256: " + afterSha256);

//                applicationUpdateLogEntity.setAfterSha256(afterSha256);

                //保存 sha256 fileList关系
//                this.fileListShaService.save(afterSha256, afterFileList);
                return null;
            }
        });


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
//        ApplicationBackupEntity applicationBackupEntity = new ApplicationBackupEntity();
//        applicationBackupEntity.setApplicationId(containerEntity.getId());
//        applicationBackupEntity.setFilePath(backUpFilePath.toString());
//        applicationBackupEntity.setSha256(beforeSha256);
//        applicationBackupEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        applicationBackupRepository.save(applicationBackupEntity);

    }

}
