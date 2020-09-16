package com.automate.ssh;


import com.automate.common.BufferedReaderTimeOutMonitor;
import com.automate.common.Charsets;
import com.automate.common.CmdResult;
import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created with IntelliJ IDEA.
 * Description: java连接远程服务器，并执行命令，接受远程服务器的执行命令的结果。
 *
 * @author: genx
 * @date: 2018-01-24 13:48
 */
public class SSHProxyImpl implements ISSHProxy {

    /**
     * 默认超时时间60秒
     */
    private static final int TIME_OUT = 60;

    private Charset charset = Charsets.UTF_GBK;

    private Session session = null;

    /**
     * 远程主机的IP地址
     */
    private final String hostName;
    /**
     * ssh连接的远程端口
     */
    private final int port;
    /**
     * 远程主机登录用户
     */
    private final String userName;
    /**
     * 远程主机额登录密码
     */
    private final String passWord;

    /**
     * @Author:luzhengxian Description  初始化登录信息
     * @Date:
     */
    public SSHProxyImpl(String hostName, int port, String userName, String passWord) {
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
        this.passWord = passWord;
    }

    public SSHProxyImpl(ISSHClient sshClient) {
        this.hostName = sshClient.getSshHost();
        this.port = sshClient.getSshPort() != null ? sshClient.getSshPort() : 22;
        this.userName = sshClient.getSshUser();
        this.passWord = sshClient.getSshPwd();
    }

    public void setCharset(Charset charset) {
        if (charset != null) {
            this.charset = charset;
        }
    }

    /**
     * @Author:luzhengxian Description  创建session并且打开连接
     * @Date:
     */
    private synchronized Session getSession() throws JSchException {
        if (session == null || !session.isConnected()) {
            session = new JSch().getSession(this.userName, this.hostName, this.port);
            session.setPassword(this.passWord);

            java.util.Properties config = new java.util.Properties();
            //不进行公钥确认
            //设置第一次登陆时候的提示，可选值：（ask | yes | no）
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
        }
        return session;
    }

    @Override
    public CmdResult execCommand(String cmd) throws Exception {
        return execCommand(cmd, TIME_OUT);
    }

    @Override
    public CmdResult execCommand(String cmd, int timeOut) throws Exception {

        CmdResult sshResult = new CmdResult(cmd);

        BufferedReader br;
        ChannelExec channel = null;
        BufferedReaderTimeOutMonitor t = null;
        try {
            //打开通道，设置通道类型，和执行命令
            channel = (ChannelExec) getSession().openChannel("exec");

            channel.setCommand(cmd);

            channel.setOutputStream(null);

            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            channel.setErrStream(errStream);


            InputStream in = channel.getInputStream();

            channel.connect();

            br = new BufferedReader(new InputStreamReader(in, this.charset));

            //设置了一个超时监控器，如果超过了规定时间，则BufferedReader则关闭。
            t = new BufferedReaderTimeOutMonitor(br, timeOut * 1000L);
            t.start();

            //接受远程服务器执行命令的结果
            String lineTemp;
            while ((lineTemp = br.readLine()) != null) {
                System.out.println("@" + lineTemp);
                sshResult.addResult(lineTemp);
            }

            //这里需要等待一会  否则 exitStatus = -1
            Thread.sleep(100);

            //获取服务器返回状态
            sshResult.setExitStatus(channel.getExitStatus());

            if (channel.getExitStatus() != 0) {
                sshResult.setError(errStream.toString(this.charset.name()));
            }

            t.doNotify();
        } catch (IOException e) {
            if (t != null && t.isOverTime()) {
                throw new TimeoutException("this commond is over time");
            } else {
                e.printStackTrace();
                throw e;
            }
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }

        return sshResult;
    }

    /**
     * 上传本地文件到 远程文件夹
     * @param localFile 本地文件
     * @param remoteDir
     * @return
     */
    @Override
    public boolean uploadLocalFileToRemote(String localFile, String remoteDir) throws Exception {
        ChannelSftp channel = null;
        try {
            channel = (ChannelSftp) getSession().openChannel("sftp");
            channel.connect();
            //判断
            SftpATTRS attrrs = null;
            try {
                attrrs = channel.stat(remoteDir);
            } catch (SftpException e) {
                channel.mkdir(remoteDir);
            }
            //实例化这个接口实现类，在文件传输的时候对传输进度进行监控
            SftpProgressMonitorImpl sftpImpl = new SftpProgressMonitorImpl();
            channel.put(localFile, remoteDir, sftpImpl);
            return sftpImpl.isSuccess();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    @Override
    public boolean uploadToRemote(InputStream inputStream, String dst) throws JSchException, SftpException {
        ChannelSftp channel = null;
        try {
            channel = (ChannelSftp) getSession().openChannel("sftp");
            channel.connect();
            //实例化这个接口实现类，在文件传输的时候对传输进度进行监控
            SftpProgressMonitorImpl sftpImpl = new SftpProgressMonitorImpl();
            channel.put(inputStream, dst, sftpImpl);
            return sftpImpl.isSuccess();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    @Override
    public List<String> readRemoteFileLines(String remoteFile) throws Exception {
        ChannelSftp channel = null;
        try {
            channel = (ChannelSftp) getSession().openChannel("sftp");
            channel.connect();

            //实例化这个接口实现类，在文件传输的时候对传输进度进行监控
            SftpProgressMonitorImpl sftpImpl = new SftpProgressMonitorImpl();
            return IOUtils.readLines(channel.get(remoteFile, sftpImpl), "UTF-8");
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    /**
     * @Author:luzhengxian Description  关闭ssh连接
     * @Date:
     */
    @Override
    public void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
