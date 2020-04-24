package com.github.gnx.automate.exec;

import com.github.gnx.automate.exec.docker.DockerSSHExecTemplate;
import com.github.gnx.automate.exec.local.LocalExecTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/24 23:01
 */
@Component
public class ExecEnvConfig {

    private static String dockerHost;
    private static Integer dockerPort;
    private static String dockerImageName;


    public static IExecTemplate getExecTemplate() {
        if (StringUtils.isNoneBlank(dockerHost, dockerImageName) && dockerPort != null) {
            return new DockerSSHExecTemplate(dockerHost, dockerPort, dockerImageName);
        } else {
            return new LocalExecTemplate();
        }

    }


    @Value("${automate.exec.docker.host}")
    public void setDockerHost(String dockerHost) {
        ExecEnvConfig.dockerHost = dockerHost;
    }

    @Value("${automate.exec.docker.port}")
    public void setDockerPort(Integer dockerPort) {
        ExecEnvConfig.dockerPort = dockerPort;
    }

    @Value("${automate.exec.docker.imageName}")
    public void setDockerImageName(String dockerImageName) {
        ExecEnvConfig.dockerImageName = dockerImageName;
    }
}
