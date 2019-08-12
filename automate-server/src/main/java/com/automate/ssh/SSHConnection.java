package com.automate.ssh;

import com.automate.common.Charsets;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecThreadPool;
import com.automate.exec.IExecStreamMonitor;
import com.jcraft.jsch.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/24 23:46
 */
public class SSHConnection {

    private static final Logger logger = LoggerFactory.getLogger(SSHConnection.class);

    private final AtomicBoolean useing = new AtomicBoolean(false);

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final Session session;

    private long releaseTime = System.currentTimeMillis();

    public SSHConnection(String host, int port, String username, String password) throws Exception {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

        session = new JSch().getSession(username, host, port);
        session.setPassword(password);
        Properties config = new Properties();
        //不进行公钥确认
        //设置第一次登陆时候的提示，可选值：（ask | yes | no）
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
    }

    public void close() {
        session.disconnect();
    }

    public boolean isConnected() {
        return session.isConnected();
    }

    /**
     * 开始使用
     *
     * @return
     */
    public boolean acquire() {
        return useing.compareAndSet(false, true);
    }

    public boolean isUseing() {
        return useing.get();
    }


    public void release() {
        useing.set(false);
        this.releaseTime = System.currentTimeMillis();
    }

    /**
     * 执行 exec语句
     *
     * @param execCommand
     */
    public void exec(ExecCommand execCommand) {
        ChannelExec channel = null;
        Future<Integer> executeFuture = null;
        try {
            channel = (ChannelExec) this.session.openChannel("exec");

            logger.debug(execCommand.getCommand());

            channel.setCommand(execCommand.getCommand());

            channel.setOutputStream(null);
            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            channel.setErrStream(errStream);
            execCommand.start();
            channel.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(channel.getInputStream(), Charsets.UTF_8));

            final ChannelExec temp = channel;
            executeFuture = ExecThreadPool.submit(() -> {
                //接受远程服务器执行命令的结果
                String lineTemp;
                while ((lineTemp = br.readLine()) != null) {
                    execCommand.inputRead(lineTemp);
                }
                //这里需要等待一会  否则 exitStatus = -1  就是这么奇怪
                Thread.sleep(100);
                return temp.getExitStatus();
            });


            int exitValue = executeFuture.get(execCommand.getTimeout(), execCommand.getUnit());
            execCommand.end(exitValue);

            if (exitValue != 0) {
                execCommand.errorRead(errStream.toString(Charsets.UTF_8.name()));
            }
        } catch (Exception e) {
            execCommand.errorRead("【error by exec】" + e.getClass().getName());
            // 1 表示 通用未知错误　
            execCommand.end(1);
            logger.error("exec error", e);
        } finally {
            if (executeFuture != null) {
                try {
                    executeFuture.cancel(true);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }

            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public int exec(String script, List<String> out, StringBuffer error) throws IllegalAccessException {
        ExecCommand execCommand = new ExecCommand(script, new IExecStreamMonitor() {
            @Override
            public void onStart(String command) {

            }

            @Override
            public void onMsg(String line) {
                out.add(line);
            }

            @Override
            public void onError(String line) {
                error.append(line).append(System.lineSeparator());
            }

            @Override
            public void onEnd(int exitValue) {

            }
        });
        this.exec(execCommand);
        return execCommand.getExitValue();
    }

    public void uploadLocalFileToRemote(String localFile, String remoteDir, SftpProgressMonitor sftpProgressMonitor) throws Exception {
        ChannelSftp channel = null;
        try {
            channel = (ChannelSftp) this.session.openChannel("sftp");
            channel.connect();
            try {
                //判断是否需要创建文件夹
                channel.stat(remoteDir);
            } catch (SftpException e) {
                channel.mkdir(remoteDir);
            }
            channel.put(localFile, remoteDir, sftpProgressMonitor);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public void mkdir(String dir) throws Exception {
        ChannelSftp channel = null;
        try {
            channel = (ChannelSftp) this.session.openChannel("sftp");
            channel.connect();
            try {
                //判断是否需要创建文件夹
                channel.stat(dir);
            } catch (SftpException e) {
                channel.mkdir(dir);
            }
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public int tar(String sourceDir, String targetFilePath, Appendable log) throws Exception {
        final String dir = targetFilePath.substring(0, targetFilePath.lastIndexOf("/"));

        StringBuilder cmd = new StringBuilder(512);
        cmd.append("cd ");
        cmd.append(sourceDir);
        cmd.append(" && tar -zcvf ");
        cmd.append(targetFilePath);
        cmd.append(" ./");
        ExecCommand execCommand = new ExecCommand(cmd.toString());

        this.mkdir(dir);
        this.exec(execCommand);

        log.append(execCommand.getOut().toString());
        return execCommand.getExitValue();
    }


    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean match(String host, int port, String username, String password) {
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(this.host, host);
        equalsBuilder.append(this.port, port);
        equalsBuilder.append(this.username, username);
        equalsBuilder.append(this.password, password);
        return equalsBuilder.isEquals();
    }

    public long getReleaseTime() {
        return releaseTime;
    }
}
