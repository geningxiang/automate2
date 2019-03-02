package com.automate.ssh;

import com.automate.common.Charsets;
import com.automate.common.utils.SystemUtil;
import com.automate.exec.ExecCommand;
import com.automate.exec.ExecHelper;
import com.automate.exec.ExecStreamReader;
import com.automate.exec.ExecThreadPool;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
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

            if(exitValue != 0){
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

            if(channel != null){
                channel.disconnect();
            }
        }

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
