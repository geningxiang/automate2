//package com.automate.task.background.impl;
//
//import com.automate.common.utils.SpringContextUtil;
//import com.automate.entity.*;
//import com.automate.exec.ExecCommand;
//import com.automate.exec.ExecStreamPrintMonitor;
//import com.automate.service.ApplicationService;
//import com.automate.service.ProjectPackageService;
//import com.automate.service.ServerService;
//import com.automate.ssh.SSHSession;
//import com.automate.ssh.SftpProgressMonitorImpl;
//import com.automate.task.background.AbstractBackgroundTask;
//import org.springframework.util.Assert;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.sql.Timestamp;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// * 应用更新任务
// *
// * @author: genx
// * @date: 2019/3/6 23:20
// */
//public class ApplicationUpdateBackgroundTaskImpl extends AbstractBackgroundTask {
//
//    private final ApplicationUpdateApplyEntity applicationUpdateApplyEntity;
//    private final int applicationId;
//
//    private ApplicationUpdateBackgroundTaskImpl(ApplicationUpdateApplyEntity applicationUpdateApplyEntity, int applicationId, Set<String> locks) {
//        super(locks);
//        this.applicationUpdateApplyEntity = applicationUpdateApplyEntity;
//        this.applicationId = applicationId;
//    }
//
//    public static ApplicationUpdateBackgroundTaskImpl create(ApplicationUpdateApplyEntity applicationUpdateApplyEntity, int applicationId) throws Exception {
//        if (applicationId <= 0) {
//            throw new IllegalArgumentException("applicationId is required");
//        }
//
//        Set<String> locks = new HashSet<>(16);
//        locks.add("APPLICATION_" + applicationId);
//        return new ApplicationUpdateBackgroundTaskImpl(applicationUpdateApplyEntity, applicationId, locks);
//    }
//
//    private boolean doInvoke(StringBuilder result) throws Exception {
//        ApplicationService applicationService = SpringContextUtil.getBean("applicationService", ApplicationService.class);
//        ServerService serverService = SpringContextUtil.getBean("serverService", ServerService.class);
//        ProjectPackageService projectPackageService = SpringContextUtil.getBean("projectPackageService", ProjectPackageService.class);
//
//
//        Optional<ProjectPackageEntity> projectPackageEntity = projectPackageService.findById(this.applicationUpdateApplyEntity.getProjectPackageId());
//
//        if (!projectPackageEntity.isPresent()) {
//            throw new IllegalArgumentException("未找到相应的项目产出物");
//        }
//
//        String path = projectPackageEntity.get().getFilePath();
//        Assert.hasText(path, "path is required");
//
//
//        File file = new File(path);
//        System.out.println(file.getAbsolutePath());
//
//        if (!file.exists()) {
//            throw new IOException("文件不存在:" + file.getAbsolutePath());
//        }
//
//
//        if (file.isDirectory()) {
//            throw new IOException("暂不支持文件夹,等待后续完善");
//        }
//
//        int index = file.getName().lastIndexOf(".");
//        if (index <= 0) {
//            throw new IOException("文件没有后缀?" + file.getName());
//        }
//
//        String suffix = file.getName().substring(index + 1).toLowerCase();
//        if (!"war".equals(suffix)) {
//            throw new IllegalArgumentException("暂时只支持war后缀,等待后续完善");
//        }
//
//
//        Optional<ApplicationEntity> applicationEntity = applicationService.getModel(this.applicationId);
//        if (!applicationEntity.isPresent()) {
//            throw new IllegalArgumentException("未找到相应的应用:" + applicationId);
//        }
//
//        Assert.hasText(applicationEntity.get().getSourceDir(), "请先指定容器的代码文件夹");
//
//
//        Optional<ServerEntity> serverEntity = serverService.getModel(applicationEntity.get().getServerId());
//        if (!serverEntity.isPresent()) {
//            throw new IllegalArgumentException("未找到相应的服务器:" + applicationEntity.get().getServerId());
//        }
//        final String sourceDir = applicationEntity.get().getSourceDir();
//        SSHSession s = new SSHSession(serverEntity.get());
//
//        s.doWork(sshConnection -> {
//
//            String remoteDir = "/tmp/" + System.currentTimeMillis() + "/";
//            sshConnection.uploadLocalFileToRemote(file.getAbsolutePath(), remoteDir, new SftpProgressMonitorImpl());
//
//            StringBuilder cmd = new StringBuilder(1024);
//
//            result.append("######## start to stop the container ########");
//
//            //关闭容器
//            ExecCommand stopCmd = ApplicationService.containerStop(applicationEntity.get());
//
//            result.append(stopCmd.getOut().toString());
//
//            if (stopCmd.getExitValue() != 0) {
//                throw new RuntimeException("关闭容器失败");
//            }
//
//            //TODO 备份
//
//            //删除旧代码
//            cmd.append("rm -rf ").append(sourceDir);
//
//            cmd.append(" && mkdir -p -v ").append(sourceDir);
//
//            cmd.append(" && unzip -o ").append(remoteDir).append(file.getName()).append(" -d ").append(sourceDir);
//
//            //删除 压缩包
//            cmd.append(" || rm -rf ").append(remoteDir);
//
//            result.append("######## start to update source ########");
//            result.append(cmd.toString());
//            ExecCommand execCommand = new ExecCommand(cmd.toString(), new ExecStreamPrintMonitor());
//            sshConnection.exec(execCommand);
//
//            result.append(execCommand.getOut().toString());
//
//            if (execCommand.getExitValue() != 0) {
//                throw new RuntimeException("更新容器代码失败");
//            }
//
//            //启动容器
//            ExecCommand startCmd = ApplicationService.containerStart(applicationEntity.get());
//            result.append(startCmd.getOut().toString());
//
//            if (startCmd.getExitValue() != 0) {
//                throw new RuntimeException("关闭容器失败");
//            }
//
//        });
//        return true;
//    }
//
//    @Override
//    public void run() {
//
//        StringBuilder result = new StringBuilder(10240);
//
//        boolean success;
//        try{
//            success=doInvoke(result);
//        }catch (Exception e){
//
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            result.append(sw.toString());
//
//            success = false;
//        }
//
//        ApplicationUpdateLogEntity applicationUpdateLogEntity = new ApplicationUpdateLogEntity();
//
//        applicationUpdateLogEntity.setProjectId(this.applicationUpdateApplyEntity.getProjectId());
//        applicationUpdateLogEntity.setServerId(0);
//        applicationUpdateLogEntity.setApplicationId(this.applicationId);
//        applicationUpdateLogEntity.setApplyId(this.applicationUpdateApplyEntity.getId());
//        applicationUpdateLogEntity.setUserId(0);
//        applicationUpdateLogEntity.setType(0);
//        applicationUpdateLogEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
//
//
//    }
//
//
//    @Override
//    public String getName() {
//        return "应用更新任务, applicationId=" + this.applicationId;
//    }
//
//
//}
