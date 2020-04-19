//package com.github.gnx.automate.exec.ssh;
//
//import com.github.gnx.automate.common.thread.GlobalThreadPoolManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.concurrent.ConcurrentLinkedQueue;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @author: genx
// * @date: 2019/2/25 22:15
// */
//public class ConnectionPool {
//
//    private static final long KEEP_TIMESTAMP = 60000L;
//    private static final ConcurrentLinkedQueue<SSHConnection> connections = new ConcurrentLinkedQueue();
//
//    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
//
//    static {
//        //启动 SSH连接池监控器  暂时用最简单的 SLEEP的形式
//        GlobalThreadPoolManager.getInstance().execute(new ConnectionMonitor());
//    }
//
//    public static SSHConnection get(String hostname, int port, String username, String password) throws Exception {
//        for (SSHConnection connection : connections) {
//            if (connection.match(hostname, port, username, password) && connection.acquire()) {
//                if (connection.isConnected()) {
//                    logger.debug("复用SSH连接,{}:{}", connection.getHost(), connection.getPort());
//                    return connection;
//                } else {
//                    //连接已断开
//                    connections.remove(connection);
//                }
//            }
//        }
//        SSHConnection sshConnection = new SSHConnection(hostname, port, username, password);
//        sshConnection.acquire();
//        logger.debug("创建SSH连接,{}:{}", sshConnection.getHost(), sshConnection.getPort());
//        connections.add(sshConnection);
//        return sshConnection;
//    }
//
//
//    /**
//     * 关闭并移除连接
//     *
//     * @param sshConnection
//     */
//    public static void close(SSHConnection sshConnection) {
//        if (sshConnection != null) {
//            sshConnection.acquire();
//            sshConnection.close();
//            //连接已断开
//            connections.remove(sshConnection);
//        }
//    }
//
//    /**
//     * 释放连接  由pool控制什么时候断开,在断开前允许复用
//     *
//     * @param sshConnection
//     */
//    public static void release(SSHConnection sshConnection) {
//        sshConnection.release();
//    }
//
//
//    private static class ConnectionMonitor implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                if (!connections.isEmpty()) {
//                    long n = System.currentTimeMillis() - KEEP_TIMESTAMP;
//                    for (SSHConnection connection : connections) {
//                        if (!connection.isUseing() && connection.getReleaseTime() < n && connection.acquire()) {
//                            logger.debug("SSH连接池监控器,移除过期连接:{}", connection.getHost());
//                            connection.close();
//                            connections.remove(connection);
//                        }
//                    }
//                }
//                try {
//                    Thread.sleep(KEEP_TIMESTAMP);
//                } catch (InterruptedException e) {
//                    logger.info("onInterruptedException");
//                    return;
//                }
//            }
//
//        }
//    }
//
//}
