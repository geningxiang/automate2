package com.github.gnx.automate.exec.docker;

import com.github.gnx.automate.exec.AbstractExecTemplate;
import com.github.gnx.automate.exec.IExecConnection;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/20 22:52
 */
public class DockerSSHExecTemplate extends AbstractExecTemplate {

    private final String host;
    private final int port;
    private final String sourceImageName;

    public DockerSSHExecTemplate(String host, int port, String sourceImageName) {
        this.host = host;
        this.port = port;
        this.sourceImageName = sourceImageName;
    }


    @Override
    protected IExecConnection createConnection() throws Exception {
        return new DockerSSHConnetction(host, port, sourceImageName);
    }
}
