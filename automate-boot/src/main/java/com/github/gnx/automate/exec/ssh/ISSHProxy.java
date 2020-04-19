//package com.github.gnx.automate.exec.ssh;
//
//import com.github.gnx.automate.common.CmdResult;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.SftpException;
//
//import java.io.InputStream;
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// * Description: java连接远程服务器的操作(接口)
// *
// * @author: genx
// * @date: 2018-01-24 13:47
// */
//public interface ISSHProxy {
//
//    CmdResult execCommand(String cmd, int timeOut) throws Exception;
//
//    CmdResult execCommand(String cmd) throws Exception;
//
//    /**
//     * @Author:luzhengxian Description  上传本地文件到远程linux上
//     * 使用sftp上传
//     * @Param localFile:本地文件存储路径
//     * @Param remoteDir:服务器上的文件存储路径
//     * @Date:
//     */
//    boolean uploadLocalFileToRemote(String localFile, String remoteDir) throws SftpException, JSchException, Exception;
//
//    boolean uploadToRemote(InputStream inputStream, String dst) throws JSchException, SftpException;
//
//    List<String> readRemoteFileLines(String remoteFile) throws Exception;
//
//    void close();
//}
