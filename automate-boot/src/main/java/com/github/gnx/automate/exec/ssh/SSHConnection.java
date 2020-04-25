package com.github.gnx.automate.exec.ssh;

import com.github.gnx.automate.common.Charsets;
import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.exec.ExecStreamReader;
import com.github.gnx.automate.exec.IExecConnection;
import com.jcraft.jsch.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 19:33
 */
public class SSHConnection implements IExecConnection, Closeable {

    private static Logger logger = LoggerFactory.getLogger(SSHConnection.class);

    private final int TIME_OUT = 10;

    private final Session session;


    public SSHConnection(String host, int port, String username, String password) throws Exception {
        session = new JSch().getSession(username, host, port);
        session.setPassword(password);
        Properties config = new Properties();
        //不进行公钥确认
        //设置第一次登陆时候的提示，可选值：（ask | yes | no）
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setTimeout(1000 * 60 * 10);
        session.connect();
    }

    @Override
    public int exec(String cmd, IMsgListener execListener) throws Exception {
        ChannelExec channel = null;
        try {
            channel = (ChannelExec) this.session.openChannel("exec");

            channel.setCommand(cmd);

            channel.connect();

            ExecStreamReader.submit(channel.getErrStream(), Charsets.UTF_8, execListener);

            ExecStreamReader.ExecStreamReadRunner execStreamReader = ExecStreamReader.submit(channel.getInputStream(), Charsets.UTF_8, execListener);

            if (!execStreamReader.await(TIME_OUT, TimeUnit.SECONDS)) {
                logger.warn("读取超时,发送sigint指令");
                //读取超时 发送
                OutputStream out = channel.getOutputStream();
                // send CTRL+C
                out.write(3);
                out.flush();

                execStreamReader.await(TIME_OUT, TimeUnit.SECONDS);
            }

            //这里需要等待一会  否则 exitStatus = -1  就是这么奇怪
            Thread.sleep(100);

            return channel.getExitStatus();
        } finally {
            if (channel != null) {
                try {
                    channel.disconnect();
                } catch (Exception e) {

                }
            }

        }

    }

    @Override
    public void upload(File localFile, String remoteDir, boolean withDecompression, IMsgListener execListener) throws Exception {
        ChannelSftp channel = null;
        try {
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            if (!remoteDir.endsWith("/")) {
                remoteDir += "/";
            }
            try {
                //判断是否需要创建文件夹
                channel.stat(remoteDir);
            } catch (SftpException e) {
                channel.mkdir(remoteDir);
            }

            channel.put(localFile.getAbsolutePath(), remoteDir, new SftpProgressMonitorImpl(execListener));


            if (withDecompression) {
                this.exec("cd " + remoteDir + " && tar -zxvf " + localFile.getName() + " && rm " + localFile.getName(), execListener);
            }

        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    @Override
    public File download(String remotePath, File localDir, IMsgListener execListener) {
        ChannelSftp channel = null;
        try {
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            execListener.appendLine(" == 从ssh下载文件 == ");
            execListener.appendLine("远程路径: " + remotePath + " ==> 本地路径: " + localDir.getAbsolutePath());

            Vector<ChannelSftp.LsEntry> vector = channel.ls(remotePath);
            boolean isDir = vector.get(0).getAttrs().isDir();

            if(isDir){
                //TODO 下载文件夹支持
                throw new RuntimeException("暂不支持下载文件夹");
            }

            if (localDir.exists()) {
                execListener.appendLine("[warn]文件夹已存在,将删除文件夹");
                FileUtils.deleteDirectory(localDir);
            }
            localDir.mkdirs();
            channel.get(remotePath, localDir.getAbsolutePath(), new SftpProgressMonitorImpl(execListener));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
        return localDir;
    }

    @Override
    public void close() throws IOException {
        session.disconnect();
    }
}
