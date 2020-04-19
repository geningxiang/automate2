package com.github.gnx.automate.exec.ssh;

import com.github.gnx.automate.common.Charsets;
import com.github.gnx.automate.common.IExecListener;
import com.github.gnx.automate.exec.ExecStreamReader;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 20:43
 */
class SSHConnectionTest {

    @Test
    public void test() throws Exception {
        SSHConnection sshConnection = new SSHConnection("192.168.1.190", 22, "root", "genx@linux");

        int result = sshConnection.exec("ping www.baidu.com", new IExecListener() {
            @Override
            public void onStart(String step, String msg) {

            }

            @Override
            public IExecListener onMsg(CharSequence csq) {
                System.out.println("[onMsg]" + csq);
                return null;
            }

            @Override
            public IExecListener onError(CharSequence csq) {
                return null;
            }
        });

        System.out.println(result);

        Thread.sleep(100000);

        sshConnection.close();
    }

    public static void main(String[] args) throws Exception {

        Session session;

        session = new JSch().getSession("root", "192.168.1.190", 22);
        session.setPassword("genx@linux");
        Properties config = new Properties();
        //不进行公钥确认
        //设置第一次登陆时候的提示，可选值：（ask | yes | no）
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.setTimeout(1000 * 60 * 10);

        session.connect();


        ChannelShell channel = (ChannelShell) session.openChannel("shell");

        OutputStream out = channel.getOutputStream();

        channel.connect();

        out.write("ping -w 100 www.baidu.com\n".getBytes());
        out.flush();

        Thread.sleep(1000);

        ExecStreamReader.submit(channel.getInputStream(), Charsets.UTF_8, new IExecListener() {
            @Override
            public void onStart(String step, String msg) {

            }

            @Override
            public IExecListener onMsg(CharSequence csq) {
                System.out.println("[onMsg]" + csq);
                return this;
            }

            @Override
            public IExecListener onError(CharSequence csq) {
                return this;
            }
        });


        Thread.sleep(10000);
        System.out.println("发送ctrl+c");

        // send CTRL+C
        out.write(0x03);
        out.flush();


        Thread.sleep(10000);


        System.out.println(channel.getExitStatus());


        System.out.println("## session.disconnect()");
        session.disconnect();


        Thread.sleep(10000);
    }

}