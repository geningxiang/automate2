package com.automate.ssh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: genx
 * Time: 2017-11-16 23:10
 */
public class SSHmanager {
    private static final Logger logger = LoggerFactory.getLogger(SSHmanager.class);


    private static ConcurrentHashMap<String, List<SSHConnection>> CONNECTION_POOL = new ConcurrentHashMap<>(64);


    public static ISSHProxy getSSHProxy(String hostname, int port, String username, String password) throws IOException {
        return new SSHProxyImpl(hostname, port, username, password);
    }

    public static SSHConnection getConnection(String hostname, int port, String username, String password) throws Exception {
        List<SSHConnection> list = CONNECTION_POOL.get(getKey(hostname, port, username, password));
        if(list != null){
            for (SSHConnection sshConnection : list) {
                if(sshConnection.acquire()){
                    return sshConnection;
                }
            }
        }
        SSHConnection sshConnection = new SSHConnection(hostname, port, username, password);


        return sshConnection;
    }

    private static String getKey(String hostname, int port, String username, String password){
        StringBuilder key = new StringBuilder(256);
        key.append(hostname).append(":").append(port);
        key.append("#").append(username).append("/").append(password);
        return key.toString();
    }

}