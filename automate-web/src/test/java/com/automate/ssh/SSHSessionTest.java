package com.automate.ssh;

import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/25 23:12
 */
public class SSHSessionTest {

    @Test
    public void upload() throws Exception {

        SSHSession s = new SSHSession("192.168.1.190", 22, "root", "genx@linux");

        s.doWork(sshConnection -> {

            String remoteDir = "/tmp/" + System.currentTimeMillis() + "/";
            sshConnection.uploadLocalFileToRemote("D:\\idea-workspace\\Automate2\\automate-web\\target/Automate2.war", remoteDir, new SftpProgressMonitorImpl());

            ExecCommand execCommand = new ExecCommand(Arrays.asList(
                    "rm -rf /var/webapps/tomcat-automate/webapps/ROOT",
                    "mkdir -p -v /var/webapps/tomcat-automate/webapps/ROOT",
                    "unzip -o " + remoteDir + "Automate2.war -d /var/webapps/tomcat-automate/webapps/ROOT/",
                    "rm -rf " + remoteDir
            ), new ExecStreamPrintMonitor()
            );

            sshConnection.exec(execCommand);

        }, true);
    }

    @Test
    public void doWork() throws Exception {
        SSHSession s = new SSHSession("192.168.1.190", 22, "root", "genx@linux");
        ExecCommand execCommand = new ExecCommand("/cmd/tomcat-automate.sh start");

        execCommand.setCmdStreamMonitor(new ExecStreamPrintMonitor());

        s.doWork(sshConnection ->
                sshConnection.exec(execCommand)
        );

        System.out.println(execCommand.getExitValue());


        System.out.println(execCommand.getOut());


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