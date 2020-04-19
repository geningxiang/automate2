package com.github.gnx.automate.exec.ssh;

import com.github.gnx.automate.exec.AbstractExecTemplate;
import com.github.gnx.automate.exec.IExecConnection;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 22:33
 */
public class SSHExecTemplate extends AbstractExecTemplate {

    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public SSHExecTemplate(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Override
    protected IExecConnection createConnection() throws Exception {
        return new SSHConnection(host, port, username, password);
    }
}
