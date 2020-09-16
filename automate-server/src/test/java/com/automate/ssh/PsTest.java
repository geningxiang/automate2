package com.automate.ssh;

import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/11/1 16:06
 */
public class PsTest {

    @Test
    public void test() throws Exception {
        SSHSession s = new SSHSession("192.168.1.190", 22, "root", "caimao@linux");
        s.doWork(sshConnection -> {

            StringBuilder cmd = new StringBuilder(10240);
            cmd.append("ps auxw");

            ExecCommand execCommand = new ExecCommand(cmd.toString(), new ExecStreamPrintMonitor());
            execCommand.setTimeOut(TimeUnit.MINUTES, 10);

            sshConnection.exec(execCommand);

        }, true);
    }
}
