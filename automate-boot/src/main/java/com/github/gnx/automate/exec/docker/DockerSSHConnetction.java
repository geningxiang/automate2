package com.github.gnx.automate.exec.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.InspectExecResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.gnx.automate.common.IExecListener;
import com.github.gnx.automate.exec.IExecConnection;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
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

    private final int TIME_OUT = 120;

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
    public int exec(String cmd, IExecListener execListener) throws Exception {
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
    public void upload(File localFile, String remoteDir, String fileName, IExecListener execListener) throws Exception {

    }

    @Override
    public void download(String remotePath, File localFile, IExecListener execListener) throws Exception {

    }

    @Override
    public void close() throws IOException {
        dockerClient.removeContainerCmd(containerId).withForce(true).exec();
        dockerClient.close();
    }
}
