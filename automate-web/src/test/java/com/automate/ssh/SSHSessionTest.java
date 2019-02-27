package com.automate.ssh;

import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import com.automate.exec.IExecStreamMonitor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/25 23:12
 */
public class SSHSessionTest {

    @Test
    public void doWork() throws Exception {
        SSHSession s = new SSHSession("192.168.1.190", 22, "root", "genx@linux");
        ExecCommand execCommand = new ExecCommand("/cmd/tomcat-automate.sh status");

        execCommand.setCmdStreamMonitor(new ExecStreamPrintMonitor());

        s.doWork(sshConnection ->
                sshConnection.exec(execCommand)
        );

        System.out.println(execCommand.getExitValue());


        System.out.println(execCommand.getOut());

        System.out.println(execCommand.getError());

//        SSHSession s1 = new SSHSession("47.100.63.232", 22, "root", "Genx@linux");
//
//        s1.doWork(sshConnection -> {
//            ExecCommand execCommand = new ExecCommand("/cmd/tomcat-automate.sh start");
//
//            sshConnection.exec(execCommand);
//
//            System.out.println(execCommand.getOut());
//
//            System.out.println(execCommand.getExitValue());
//        });


    }
}