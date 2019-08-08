package com.automate.task.background.update;

import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.ApplicationEntity;
import com.automate.entity.ApplicationUpdateApplyEntity;
import com.automate.entity.ProjectPackageEntity;
import com.automate.entity.ServerEntity;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import com.automate.service.ApplicationService;
import com.automate.service.ProjectPackageService;
import com.automate.service.ServerService;
import com.automate.ssh.SSHSession;
import com.automate.ssh.SSHUtil;
import com.automate.ssh.SftpProgressMonitorImpl;
import com.automate.task.background.AbstractBackgroundTask;
import com.automate.task.background.BackgroundLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/8 23:34
 */
public class BackgroundUpdateTask extends AbstractBackgroundTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ApplicationUpdateApplyEntity applicationUpdateApplyEntity;

    public BackgroundUpdateTask(ApplicationUpdateApplyEntity applicationUpdateApplyEntity) throws Exception {
        super(new BackgroundLock.Builder().addLockByApplication(applicationUpdateApplyEntity.getApplicationId()));
        this.applicationUpdateApplyEntity = applicationUpdateApplyEntity;

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void run() {

        ProjectPackageService projectPackageService = SpringContextUtil.getBean("projectPackageService", ProjectPackageService.class);
        ApplicationService applicationService = SpringContextUtil.getBean("applicationService", ApplicationService.class);
        ServerService serverService = SpringContextUtil.getBean("serverService", ServerService.class);

        Optional<ProjectPackageEntity> projectPackageEntity = projectPackageService.findById(this.applicationUpdateApplyEntity.getProjectPackageId());
        if (!projectPackageEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的更新包:" + this.applicationUpdateApplyEntity.getProjectPackageId());
        }

        File file = new File(projectPackageEntity.get().getFilePath());


        if (!file.exists()) {
            throw new IllegalArgumentException("文件不存在:" + file.getAbsolutePath());
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

        Optional<ApplicationEntity> applicationEntity = applicationService.findById(this.applicationUpdateApplyEntity.getApplicationId());
        if (!applicationEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的容器:" + this.applicationUpdateApplyEntity.getApplicationId());
        }

        Optional<ServerEntity> serverEntity = serverService.getModel(applicationEntity.get().getServerId());

        if (!serverEntity.isPresent()) {
            throw new IllegalArgumentException("未找到相应的服务器:" + applicationEntity.get().getServerId());
        }

        final String sourceDir = applicationEntity.get().getSourceDir();
        SSHSession s = new SSHSession(serverEntity.get());



        final StringBuilder execLog = new StringBuilder(10240);

        try {

            //当前源码的filelist
            List<String[]> fileSha256List = SSHUtil.sha256sum(s, sourceDir);

            //TODO 与更新包 比较 sha256

            s.doWork(sshConnection -> {

                List<String> msgList = new ArrayList(1024);
                StringBuffer error = new StringBuffer();
                sshConnection.exec("ls", msgList, error);

                String remoteDir = "/tmp/" + System.currentTimeMillis() + "/";
                sshConnection.uploadLocalFileToRemote(file.getAbsolutePath(), remoteDir, new SftpProgressMonitorImpl());

                StringBuilder cmd = new StringBuilder(1024);

                execLog.append("######## start to stop the container ########").append(System.lineSeparator());

                //关闭容器
                ExecCommand stopCmd = ApplicationService.containerStop(applicationEntity.get());

                execLog.append(stopCmd.getOut()).append(System.lineSeparator());

                if (stopCmd.getExitValue() != 0) {
                    throw new RuntimeException("关闭容器失败");
                }

                //TODO 备份

                //删除旧代码
                cmd.append("rm -rf ").append(sourceDir);

                cmd.append(" && mkdir -p -v ").append(sourceDir);

                cmd.append(" && unzip -o ").append(remoteDir).append(file.getName()).append(" -d ").append(sourceDir);

                //删除 压缩包
                cmd.append(" || rm -rf ").append(remoteDir);

                execLog.append("######## start to update source ########").append(System.lineSeparator());
                execLog.append(cmd).append(System.lineSeparator());
                ExecCommand execCommand = new ExecCommand(cmd.toString(), new ExecStreamPrintMonitor());
                sshConnection.exec(execCommand);

                execLog.append(execCommand.getOut()).append(System.lineSeparator());

                if (execCommand.getExitValue() != 0) {
                    throw new RuntimeException("更新容器代码失败");
                }

                //启动容器
                ExecCommand startCmd = ApplicationService.containerStart(applicationEntity.get());
                execLog.append(startCmd.getOut()).append(System.lineSeparator());

                if (startCmd.getExitValue() != 0) {
                    throw new RuntimeException("关闭容器失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}