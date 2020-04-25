package com.github.gnx.automate.exec.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.common.utils.TarUtils;
import com.github.gnx.automate.exec.IExecConnection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 20:17
 */
public class DockerSSHConnetction implements IExecConnection {

    private static Logger logger = LoggerFactory.getLogger(DockerSSHConnetction.class);

    private final int TIME_OUT = 600;

    private final DockerClient dockerClient;
    private final String containerId;

    public DockerSSHConnetction(String host, int port, String sourceImageName) {
        dockerClient = DockerClientBuilder.getInstance("tcp://" + host + ":" + port).build();
        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(sourceImageName);
        createContainerCmd.withName("automate_" + FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(new Date()));

        //分配一个伪终端 防止容器直接退出
        createContainerCmd.withTty(true);

        CreateContainerResponse createContainerResponse = createContainerCmd.exec();
        containerId = createContainerResponse.getId();

        dockerClient.startContainerCmd(containerId).exec();
    }


    @Override
    public int exec(String cmd, IMsgListener execListener) throws Exception {
        ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withCmd("bash", "-c", cmd)
                .exec();
        ExecStartResultCallback execStartResultCallback = dockerClient.execStartCmd(execCreateCmdResponse.getId()).exec(
                new ExecStartResultCallback(execListener));

        boolean isTimeOut = false;
        if (!execStartResultCallback.awaitCompletion(TIME_OUT, TimeUnit.SECONDS)) {
            //超时 取消
            isTimeOut = true;
            throw new TimeoutException("执行超时");

        }

        InspectExecResponse inspectExecResponse = dockerClient.inspectExecCmd(execCreateCmdResponse.getId()).exec();

        if (isTimeOut) {
            //TODO 超时如何优雅关闭
            ExecCreateCmdResponse killCmd = dockerClient.execCreateCmd(containerId)
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withCmd("bash", "-c", "kill -SIGINT " + inspectExecResponse.getPidLong())
                    .exec();

            dockerClient.execStartCmd(killCmd.getId()).exec(new ExecStartResultCallback(execListener)).awaitCompletion();
            execStartResultCallback.awaitCompletion();
        }
        return inspectExecResponse.getExitCodeLong().intValue();
    }

    @Override
    public void upload(File localFile, String remoteDir, boolean withDecompression, IMsgListener execListener) throws Exception {
        //docker 没有文件夹会报错

        this.exec("mkdir " + remoteDir, execListener);
        CopyArchiveToContainerCmd copyArchiveToContainerCmd = dockerClient.copyArchiveToContainerCmd(this.containerId);
        if (withDecompression) {
            copyArchiveToContainerCmd.withTarInputStream(new FileInputStream(localFile));
        } else {
            copyArchiveToContainerCmd.withHostResource(localFile.getAbsolutePath());
        }
        copyArchiveToContainerCmd.withRemotePath(remoteDir).exec();
    }

    @Override
    public File download(String remotePath, File localDir, IMsgListener execListener) throws Exception {

        remotePath = remotePath.replace("\\", "/");

        String lastName = remotePath.substring(remotePath.lastIndexOf("/") + 1);

        execListener.appendLine(" == 从docker下载文件 == ");
        execListener.appendLine("远程路径: " + remotePath + " ==> 本地文件夹: " + localDir.getAbsolutePath());

        if (localDir.exists()) {
            execListener.appendLine("[warn]文件夹已存在,将删除文件夹");
            FileUtils.deleteDirectory(localDir);
        }

        if (!localDir.mkdirs()) {
            throw new IOException("创建文件夹失败: " + localDir.getAbsolutePath());
        }

        File tarFile = new File(localDir.getAbsolutePath() + "/temp.tar");
        try (InputStream in = dockerClient.copyArchiveFromContainerCmd(this.containerId, remotePath).exec();
             FileOutputStream out = new FileOutputStream(tarFile)
        ) {
            // in 是 tar的形式
            IOUtils.copy(in, out);

            TarUtils.unTar(tarFile, localDir);
        } finally {
            tarFile.deleteOnExit();
        }
        File result = new File(localDir.getAbsolutePath() + File.separator + lastName);
        execListener.appendLine("下载完成, 得到" + (result.isDirectory() ? "文件夹" : "文件") + ": " + result.getAbsolutePath());
        return result;

    }

    @Override
    public void close() throws IOException {
        dockerClient.removeContainerCmd(containerId).withForce(true).exec();
        dockerClient.close();
    }
}
