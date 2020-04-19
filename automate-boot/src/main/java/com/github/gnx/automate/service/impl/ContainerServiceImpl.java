package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.common.IExecListener;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ProductEntity;
import com.github.gnx.automate.entity.ServerEntity;
import com.github.gnx.automate.exec.ssh.SSHConnection;
import com.github.gnx.automate.repository.ContainerRepository;
import com.github.gnx.automate.repository.ProductRepository;
import com.github.gnx.automate.repository.ServerRepository;
import com.github.gnx.automate.service.IContainerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

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
    public List<ContainerEntity> getAllByProjectIdOrderById(int projectId) {
        return containerRepository.getAllByProjectIdOrderById(projectId);
    }

    @Override
    public void update(int packageId, int containerId, IExecListener msgLineReader) throws Exception {

        ProductEntity productEntity = productRepository.findById(packageId).get();
        ContainerEntity containerEntity = containerRepository.findById(containerId).get();
        ServerEntity serverEntity = serverRepository.findById(containerEntity.getServerId()).get();


        File file = this.updateFileVerify(productEntity.getFilePath());

        //源代码文件夹
        final String sourceDir = containerEntity.getSourceDir();

        /*
        SSHSession s = new SSHSession(serverEntity);

        //当前源码的filelist
        List<String[]> fileSha256List = SSHUtil.sha256sum(s, sourceDir);

        String fileList = FileListSha256Util.parseToFileListByArray(fileSha256List);
        String beforeSha256 = DigestUtils.sha256Hex(fileList);
//        //保存 sha256 fileList关系
//        this.fileListShaService.save(beforeSha256, fileList);
//
//        ApplicationUpdateLogEntity applicationUpdateLogEntity = create(applicationEntity.get(), applicationUpdateApplyEntity.getId(), beforeSha256);


        try {


            s.doWork(sshConnection -> {
                msgLineReader.onMsg("已建立ssh连接: " + serverEntity.getSshHost() + ":" + serverEntity.getSshPort());

                msgLineReader.onMsg("开始上传文件 " + file.getAbsolutePath() + " ==>> " + containerEntity.getUploadDir());

                sshConnection.uploadLocalFileToRemote(file.getAbsolutePath(), containerEntity.getUploadDir(), new SftpProgressMonitorImpl(msgLineReader));

                String uploadedFile = containerEntity.getUploadDir() + "/" + file.getName();


                //2.备份
                backup(containerEntity, sshConnection, beforeSha256, msgLineReader);


                msgLineReader.onMsg("######## 关闭应用 ########");

                //关闭容器
                ExecCommand stopCmd = ContainerUtil.containerStop(sshConnection, containerEntity);

                msgLineReader.onMsg(stopCmd.getOut().toString());

                if (stopCmd.getExitValue() != 0) {
                    throw new RuntimeException("关闭应用失败");
                }

                StringBuilder cmd = new StringBuilder(1024);
                //删除旧代码
                cmd.append("rm -rf ").append(sourceDir);

                cmd.append(" && mkdir -p -v ").append(sourceDir);
                //解压
                cmd.append(" && unzip -o ").append(uploadedFile).append(" -d ").append(sourceDir);

                //删除 上传的包
//                cmd.onMsg(" || rm ").onMsg(uploadedFile);

                msgLineReader.onMsg("######## 开始更新应用 ########");
                msgLineReader.onMsg(cmd.toString());
                ExecCommand execCommand = new ExecCommand(cmd.toString(), new ExecStreamPrintMonitor());
                sshConnection.exec(execCommand);

                msgLineReader.onMsg(execCommand.getOut().toString());

                if (execCommand.getExitValue() != 0) {
                    throw new RuntimeException("更新应用失败");
                }


                //更新后的文件sha256
                List<String[]> afterFileSha256List = SSHUtil.sha256sum(sshConnection, sourceDir);
                String afterFileList = FileListSha256Util.parseToFileListByArray(afterFileSha256List);
                String afterSha256 = DigestUtils.sha256Hex(afterFileList);

//                applicationUpdateLogEntity.setAfterSha256(afterSha256);

                //保存 sha256 fileList关系
//                this.fileListShaService.save(afterSha256, afterFileList);

                //启动容器
                ExecCommand startCmd = ContainerUtil.containerStart(sshConnection, containerEntity);
                msgLineReader.onMsg(startCmd.getOut().toString());

                if (startCmd.getExitValue() != 0) {
                    throw new RuntimeException("启动容器失败");
                }
            });


//            applicationUpdateLogEntity.setStatus(AssemblyLineLogEntity.Status.success);
//            applicationUpdateLogEntity.setLog(execLog.toString());
//            applicationUpdateLogEntity.setDoneTime(new Timestamp(System.currentTimeMillis()));
//            applicationUpdateLogRepository.save(applicationUpdateLogEntity);

        } catch (Exception e) {
            msgLineReader.onMsg(ExceptionUtils.getStackTrace(e));
//            applicationUpdateLogEntity.setStatus(AssemblyLineLogEntity.Status.error);
//            applicationUpdateLogEntity.setLog(execLog.toString());
//            applicationUpdateLogEntity.setDoneTime(new Timestamp(System.currentTimeMillis()));
//            applicationUpdateLogRepository.save(applicationUpdateLogEntity);
        }

    */
    }


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


    private void backup(ContainerEntity containerEntity, SSHConnection sshConnection, String beforeSha256, IExecListener msgLineReader) throws Exception {
        if (StringUtils.isBlank(containerEntity.getBackupDir())) {
            msgLineReader.onMsg("[WARN]当前应用未设置备份文件夹");
            return;
        }

        msgLineReader.onMsg("====== 开始备份 ======");
        StringBuilder backUpFilePath = new StringBuilder(256);
        backUpFilePath.append(containerEntity.getBackupDir());
        if (!containerEntity.getBackupDir().endsWith("/")) {
            backUpFilePath.append("/");
        }
        backUpFilePath.append(FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(System.currentTimeMillis())).append(".tar.gz");

        logger.info("{} ==> {}", containerEntity.getSourceDir(), backUpFilePath.toString());

        msgLineReader.onMsg(containerEntity.getSourceDir() + " ==> " + backUpFilePath.toString());

        //原文件夹 压缩
//        sshConnection.tar(containerEntity.getSourceDir(), backUpFilePath.toString(), msgLineReader);

        msgLineReader.onMsg("====== 备份结束 ======");

        //保存应用备份表
//        ApplicationBackupEntity applicationBackupEntity = new ApplicationBackupEntity();
//        applicationBackupEntity.setApplicationId(containerEntity.getId());
//        applicationBackupEntity.setFilePath(backUpFilePath.toString());
//        applicationBackupEntity.setSha256(beforeSha256);
//        applicationBackupEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        applicationBackupRepository.save(applicationBackupEntity);

    }

}
