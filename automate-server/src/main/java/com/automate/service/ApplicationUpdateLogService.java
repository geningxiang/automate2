package com.automate.service;

import com.automate.common.utils.FileListSha256Util;
import com.automate.entity.*;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import com.automate.repository.*;
import com.automate.ssh.SSHConnection;
import com.automate.ssh.SSHSession;
import com.automate.ssh.SSHUtil;
import com.automate.ssh.SftpProgressMonitorImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/11 11:41
 */
@Service
public class ApplicationUpdateLogService {

    @Autowired
    private ApplicationUpdateLogRepository applicationUpdateLogRepository;

    @Autowired
    private ProjectPackageRepository projectPackageRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationBackupRepository applicationBackupRepository;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private FileListShaService fileListShaService;

    public Page<ApplicationUpdateLogEntity> findAll(Specification specification, Pageable pageable) {
        return applicationUpdateLogRepository.findAll(specification, pageable);
    }

    public Page<ApplicationUpdateLogEntity> findAll(Pageable pageable) {
        return applicationUpdateLogRepository.findAll(pageable);
    }

    public Optional<ApplicationUpdateLogEntity> findById(int id) {
        return applicationUpdateLogRepository.findById(id);
    }


    public void doUpdate(ApplicationUpdateApplyEntity applicationUpdateApplyEntity) throws Exception {
        Optional<ProjectPackageEntity> projectPackageEntity = projectPackageRepository.findById(applicationUpdateApplyEntity.getProjectPackageId());
        if (!projectPackageEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的更新包:" + applicationUpdateApplyEntity.getProjectPackageId());
        }
        File file = this.updateFileVerify(projectPackageEntity.get().getFilePath());

        Optional<ApplicationEntity> applicationEntity = applicationRepository.findById(applicationUpdateApplyEntity.getApplicationId());
        if (!applicationEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的容器:" + applicationUpdateApplyEntity.getApplicationId());
        }

        Optional<ServerEntity> serverEntity = serverRepository.findById(applicationEntity.get().getServerId());
        if (!serverEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的服务器:" + applicationEntity.get().getServerId());
        }

        //源代码文件夹
        final String sourceDir = applicationEntity.get().getSourceDir();

        SSHSession s = new SSHSession(serverEntity.get());

        //当前源码的filelist
        List<String[]> fileSha256List = SSHUtil.sha256sum(s, sourceDir);

        String fileList = FileListSha256Util.parseToFileListByArray(fileSha256List);
        String beforeSha256 = DigestUtils.sha256Hex(fileList);
        //保存 sha256 fileList关系
        this.fileListShaService.save(beforeSha256, fileList);

        ApplicationUpdateLogEntity applicationUpdateLogEntity = create(applicationEntity.get(), applicationUpdateApplyEntity.getId(), beforeSha256);
        final StringBuilder execLog = new StringBuilder(10240);

        try {


            s.doWork(sshConnection -> {

                //1.上传更新文件
                String remoteDir = "/tmp/" + System.currentTimeMillis() + "/";
                sshConnection.uploadLocalFileToRemote(file.getAbsolutePath(), remoteDir, new SftpProgressMonitorImpl());

                //2.备份
                backup(applicationEntity.get(), sshConnection, beforeSha256, execLog);


                execLog.append("######## 关闭应用 ########").append(System.lineSeparator());

                //关闭容器
                ExecCommand stopCmd = ApplicationService.containerStop(sshConnection, applicationEntity.get());

                execLog.append(stopCmd.getOut()).append(System.lineSeparator());

                if (stopCmd.getExitValue() != 0) {
                    throw new RuntimeException("关闭应用失败");
                }

                StringBuilder cmd = new StringBuilder(1024);
                //删除旧代码
                cmd.append("rm -rf ").append(sourceDir);

                cmd.append(" && mkdir -p -v ").append(sourceDir);
                //解压
                cmd.append(" && unzip -o ").append(remoteDir).append(file.getName()).append(" -d ").append(sourceDir);

                //删除 压缩包
                cmd.append(" || rm -rf ").append(remoteDir);

                execLog.append("######## 开始更新应用 ########").append(System.lineSeparator());
                execLog.append(cmd).append(System.lineSeparator());
                ExecCommand execCommand = new ExecCommand(cmd.toString());
                sshConnection.exec(execCommand);

                execLog.append(execCommand.getOut()).append(System.lineSeparator());

                if (execCommand.getExitValue() != 0) {
                    throw new RuntimeException("更新应用失败");
                }


                //更新后的文件sha256
                List<String[]> afterFileSha256List = SSHUtil.sha256sum(sshConnection, sourceDir);
                String afterFileList = FileListSha256Util.parseToFileListByArray(afterFileSha256List);
                String afterSha256 = DigestUtils.sha256Hex(afterFileList);
                applicationUpdateLogEntity.setAfterSha256(afterSha256);

                //保存 sha256 fileList关系
                this.fileListShaService.save(afterSha256, afterFileList);

                //启动容器
                ExecCommand startCmd = ApplicationService.containerStart(sshConnection, applicationEntity.get());
                execLog.append(startCmd.getOut()).append(System.lineSeparator());

                if (startCmd.getExitValue() != 0) {
                    throw new RuntimeException("启动容器失败");
                }
            });


            applicationUpdateLogEntity.setStatus(AssemblyLineLogEntity.Status.success);
            applicationUpdateLogEntity.setLog(execLog.toString());
            applicationUpdateLogEntity.setDoneTime(new Timestamp(System.currentTimeMillis()));
            applicationUpdateLogRepository.save(applicationUpdateLogEntity);

        } catch (Exception e) {
            execLog.append(ExceptionUtils.getStackTrace(e));
            applicationUpdateLogEntity.setStatus(AssemblyLineLogEntity.Status.error);
            applicationUpdateLogEntity.setLog(execLog.toString());
            applicationUpdateLogEntity.setDoneTime(new Timestamp(System.currentTimeMillis()));
            applicationUpdateLogRepository.save(applicationUpdateLogEntity);
        }

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

    private ApplicationUpdateLogEntity create(ApplicationEntity applicationEntity, int applyId, String beforeSha256) {
        ApplicationUpdateLogEntity applicationUpdateLogEntity = new ApplicationUpdateLogEntity();
        applicationUpdateLogEntity.setProjectId(applicationEntity.getProjectId());
        applicationUpdateLogEntity.setServerId(applicationEntity.getServerId());
        applicationUpdateLogEntity.setApplicationId(applicationEntity.getId());
        applicationUpdateLogEntity.setApplyId(applyId);
        applicationUpdateLogEntity.setUserId(0);
        applicationUpdateLogEntity.setType(0);
        applicationUpdateLogEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        applicationUpdateLogEntity.setStatus(AssemblyLineLogEntity.Status.running);
        applicationUpdateLogEntity.setBeforeSha256(beforeSha256);
        applicationUpdateLogRepository.save(applicationUpdateLogEntity);
        return applicationUpdateLogEntity;
    }

    private void backup(ApplicationEntity applicationEntity, SSHConnection sshConnection, String beforeSha256, StringBuilder execLog) throws Exception {
        if (StringUtils.isBlank(applicationEntity.getBackupDir())) {
            execLog.append("[WARN]当前应用未设置备份文件夹").append(System.lineSeparator());
            return;
        }

        execLog.append("====== 开始备份 ======").append(System.lineSeparator());
        StringBuilder backUpFilePath = new StringBuilder(256);
        backUpFilePath.append(applicationEntity.getBackupDir());
        if (!applicationEntity.getBackupDir().endsWith("/")) {
            backUpFilePath.append(backUpFilePath.append("/"));
        }
        backUpFilePath.append(FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(System.currentTimeMillis())).append(".tar.gz");

        //原文件夹 压缩
        sshConnection.tar(applicationEntity.getSourceDir(), backUpFilePath.toString(), execLog);

        execLog.append("====== 备份结束 ======").append(System.lineSeparator());

        //保存应用备份表
        ApplicationBackupEntity applicationBackupEntity = new ApplicationBackupEntity();
        applicationBackupEntity.setApplicationId(applicationEntity.getId());
        applicationBackupEntity.setFilePath(backUpFilePath.toString());
        applicationBackupEntity.setSha256(beforeSha256);
        applicationBackupEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        applicationBackupRepository.save(applicationBackupEntity);

    }

}
