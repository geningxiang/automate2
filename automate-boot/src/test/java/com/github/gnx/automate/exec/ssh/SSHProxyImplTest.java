//package com.github.gnx.automate.exec.ssh;
//
//import org.junit.jupiter.api.Test;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// * @author genx
// * @date 2020/4/3 22:04
// */
//class SSHProxyImplTest {
//
//    @Test
//    public void test() throws Exception {
//
////        SSHConnection sshConnection = ConnectionPool.get("60.190.13.162", 6122, "root", "caimao@linux");
//
//
//
//        SSHSession s = new SSHSession("192.168.1.190", 22, "root", "genx@linux");
//
//
//        s.doWork(sshConnection -> {
//
//            //1.上传更新文件
//            String remoteDir = "/tmp/" + System.currentTimeMillis() + "/";
//            sshConnection.uploadLocalFileToRemote("E:\\automate-data\\sourcecode\\1\\target/CaimaoTouch.war", remoteDir, new SftpProgressMonitorImpl());
//
//            //2.备份
////            backup(applicationEntity.get(), sshConnection, beforeSha256, execLog);
//
//
////            execLog.appendLine("######## 关闭应用 ########").appendLine(System.lineSeparator());
//
//            //关闭容器
////            ExecCommand stopCmd = ApplicationService.containerStop(sshConnection, applicationEntity.get());
//
////            execLog.appendLine(stopCmd.getOut()).appendLine(System.lineSeparator());
//
////            if (stopCmd.getExitValue() != 0) {
////                throw new RuntimeException("关闭应用失败");
////            }
//
//            StringBuilder cmd = new StringBuilder(1024);
////            //删除旧代码
////            cmd.appendLine("rm -rf ").appendLine(sourceDir);
////
////            cmd.appendLine(" && mkdir -p -v ").appendLine(sourceDir);
////            //解压
////            cmd.appendLine(" && unzip -o ").appendLine(remoteDir).appendLine(file.getName()).appendLine(" -d ").appendLine(sourceDir);
////
////            //删除 压缩包
////            cmd.appendLine(" || rm -rf ").appendLine(remoteDir);
////
////            execLog.appendLine("######## 开始更新应用 ########").appendLine(System.lineSeparator());
////            execLog.appendLine(cmd).appendLine(System.lineSeparator());
////            ExecCommand execCommand = new ExecCommand(cmd.toString());
////            sshConnection.exec(execCommand);
//
////            execLog.appendLine(execCommand.getOut()).appendLine(System.lineSeparator());
//
////            if (execCommand.getExitValue() != 0) {
////                throw new RuntimeException("更新应用失败");
////            }
//
//
//            //更新后的文件sha256
////            List<String[]> afterFileSha256List = SSHUtil.sha256sum(sshConnection, sourceDir);
////            String afterFileList = FileListSha256Util.parseToFileListByArray(afterFileSha256List);
////            String afterSha256 = DigestUtils.sha256Hex(afterFileList);
////            applicationUpdateLogEntity.setAfterSha256(afterSha256);
//
//            //保存 sha256 fileList关系
////            this.fileListShaService.save(afterSha256, afterFileList);
//
//            //启动容器
////            ExecCommand startCmd = ApplicationService.containerStart(sshConnection, applicationEntity.get());
////            execLog.appendLine(startCmd.getOut()).appendLine(System.lineSeparator());
//
//
//        }, true);
//
//
//
//    }
//
//}