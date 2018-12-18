package com.automate.ssh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: genx
 * Time: 2017-11-16 23:10
 */
public class SSHmanager {
    private static final Logger logger = LoggerFactory.getLogger(SSHmanager.class);


    public static ISSHProxy getSSHProxy(String hostname, int port, String username, String password) throws IOException {
        return new SSHProxyImpl(hostname, port, username, password);
    }

}