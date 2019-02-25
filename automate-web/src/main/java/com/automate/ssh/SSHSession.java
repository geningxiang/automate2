package com.automate.ssh;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/25 23:09
 */
public class SSHSession {
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public SSHSession(String host, int port, String username, String password){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }


    public void doWork(SSHWork sshWork) throws Exception {
        doWork(sshWork, false);
    }

    public void doWork(SSHWork sshWork, boolean close) throws Exception {
        SSHConnection sshConnection = ConnectionPool.get(host, port, username, password);
        try {
            sshWork.execute(sshConnection);
        } finally {
            if(close){
                ConnectionPool.close(sshConnection);
            } else {
                sshConnection.release();
            }

        }
    }

}
