package com.automate.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/24 23:46
 */
public class SSHConnection {

    private final AtomicBoolean useing = new AtomicBoolean(false);

    private final Session session;

    public SSHConnection(String host, int port, String username, String password) throws Exception {
        session = new JSch().getSession(username, host, port);
        session.setPassword(password);
        Properties config = new Properties();
        //不进行公钥确认
        //设置第一次登陆时候的提示，可选值：（ask | yes | no）
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
    }

    public void close(){
        session.disconnect();
    }

    /**
     * 开始使用
     * @return
     */
    public boolean acquire(){
        return useing.compareAndSet(false, true);
    }


    public void release(){
        useing.set(false);
    }
}
