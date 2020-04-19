//package com.github.gnx.automate.exec.ssh;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @author: genx
// * @date: 2019/2/25 23:09
// */
//public class SSHSession {
//    private static final Logger logger = LoggerFactory.getLogger(SSHSession.class);
//
//    private final String host;
//    private final int port;
//    private final String username;
//    private final String password;
//
//    public SSHSession(String host, int port, String username, String password){
//        this.host = host;
//        this.port = port;
//        this.username = username;
//        this.password = password;
//    }
//
//    public SSHSession(ISSHClient isshClient){
//        this.host = isshClient.getSshHost();
//        this.port = isshClient.getSshPort() != null ? isshClient.getSshPort() : 22;
//        this.username = isshClient.getSshUser();
//        this.password = isshClient.getSshPwd();
//    }
//
//
//    public void doWork(SSHWork sshWork) throws Exception {
//        doWork(sshWork, false);
//    }
//
//    public void doWork(SSHWork sshWork, boolean close) throws Exception {
//        SSHConnection sshConnection = ConnectionPool.get(host, port, username, password);
//        try {
//            logger.debug("开始使用ssh连接, {}:{}", sshConnection.getHost(), sshConnection.getPort());
//            sshWork.execute(sshConnection);
//        } finally {
//            if(close){
//                logger.debug("关闭ssh连接, {}:{}", sshConnection.getHost(), sshConnection.getPort());
//                ConnectionPool.close(sshConnection);
//            } else {
//                logger.debug("归还ssh连接, {}:{}", sshConnection.getHost(), sshConnection.getPort());
//                sshConnection.release();
//            }
//
//        }
//    }
//}
