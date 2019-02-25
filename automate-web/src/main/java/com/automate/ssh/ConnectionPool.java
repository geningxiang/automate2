package com.automate.ssh;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/25 22:15
 */
public class ConnectionPool {

    private static final ConcurrentLinkedQueue<SSHConnection> connections = new ConcurrentLinkedQueue();

    public static SSHConnection get(String hostname, int port, String username, String password) throws Exception {
        for (SSHConnection connection : connections) {
            if(connection.match(hostname, port, username, password) && connection.acquire()){
                if(connection.isConnected()){
                    return connection;
                } else {
                    //连接已断开
                    connections.remove(connection);
                }
            }
        }
        SSHConnection sshConnection = new SSHConnection(hostname, port, username, password);
        connections.add(sshConnection);
        return sshConnection;
    }


    /**
     * 关闭并移除连接
     * @param sshConnection
     */
    public static void close(SSHConnection sshConnection){
        if(sshConnection != null) {
            sshConnection.close();
            //连接已断开
            connections.remove(sshConnection);
        }
    }

    /**
     * 释放连接  由pool控制什么时候断开,在断开前允许复用
     * @param sshConnection
     */
    public static void release(SSHConnection sshConnection){
        sshConnection.release();
    }



}
