package com.github.gnx.automate.exec.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CopyArchiveToContainerCmd;
import com.github.dockerjava.core.DockerClientBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/20 22:52
 */
class DockerConnetctionTest {

    public static void main(String[] args) throws FileNotFoundException {
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://192.168.1.190:2375").build();
        String containerId = "08c3c2d20d13fa51011ff9d570efb87b151c4e2ef9d6228588690224841da7d5";

        CopyArchiveToContainerCmd copyArchiveToContainerCmd = dockerClient.copyArchiveToContainerCmd(containerId);
//        copyArchiveToContainerCmd.withHostResource("");

        copyArchiveToContainerCmd.withTarInputStream(new FileInputStream("E:/automate-data/sourcecode/tmp/tmp.tar.gz"));

        copyArchiveToContainerCmd.withRemotePath("/tmp/touch").exec();
    }

}