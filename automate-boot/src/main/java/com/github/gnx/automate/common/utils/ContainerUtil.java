//package com.github.gnx.automate.common.utils;
//
//import com.github.gnx.automate.entity.ContainerEntity;
//import com.github.gnx.automate.exec.ExecCommand;
//import com.github.gnx.automate.exec.ssh.SSHConnection;
//import org.springframework.util.Assert;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// * @author genx
// * @date 2020/4/4 14:38
// */
//public class ContainerUtil {
//
//
//    public static ExecCommand containerStart(SSHConnection sshConnection, ContainerEntity containerEntity) throws Exception {
//        Assert.hasText(containerEntity.getScriptStart(), "当前未配置容器启动脚本");
//        ExecCommand execCommand = new ExecCommand(containerEntity.getScriptStart());
//        sshConnection.exec(execCommand);
//        return execCommand;
//    }
//
//    public static ExecCommand containerStop(SSHConnection sshConnection, ContainerEntity containerEntity) throws Exception {
//        Assert.hasText(containerEntity.getScriptStop(), "当前未配置容器停止脚本");
//        ExecCommand execCommand = new ExecCommand(containerEntity.getScriptStop());
//        sshConnection.exec(execCommand);
//        return execCommand;
//    }
//
//
//    public static ExecCommand containerCheck(SSHConnection sshConnection, ContainerEntity containerEntity) throws Exception {
//        Assert.hasText(containerEntity.getScriptCheck(), "当前未配置容器检查脚本");
//        ExecCommand execCommand = new ExecCommand(containerEntity.getScriptCheck());
//        sshConnection.exec(execCommand);
//        return execCommand;
//    }
//
//
//}
