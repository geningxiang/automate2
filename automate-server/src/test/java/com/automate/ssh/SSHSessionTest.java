package com.automate.ssh;

import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import com.automate.exec.IExecStreamMonitor;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        SSHSession s = new SSHSession("47.100.63.232", 22, "root", "");

        s.doWork(sshConnection -> {

            String remoteDir = "/tmp/" + System.currentTimeMillis() + "/";
            String targetDir = "/www/automate-temp2/webapps/ROOT/";

            sshConnection.uploadLocalFileToRemote("D:\\idea-workspace\\Automate2\\automate-web\\target/Automate2.war", remoteDir, new SftpProgressMonitorImpl());

            ExecCommand execCommand = new ExecCommand(Arrays.asList(
                    "rm -rf " + targetDir,
                    "mkdir -p -v " + targetDir,
                    "unzip -o " + remoteDir + "Automate2.war -d " + targetDir,
                    "rm -rf " + remoteDir
            ), new ExecStreamPrintMonitor()
            );

            sshConnection.exec(execCommand);

        }, true);
    }

    @Test
    public void md5sum() throws Exception {
        SSHSession s = new SSHSession("47.100.63.232", 22, "root", "Genx@linux");

        List<String[]> list = new ArrayList(1024);
        s.doWork(sshConnection -> {

            String targetDir = "/www/automate-temp2/webapps/ROOT/";
            if(!targetDir.endsWith("/")){
                targetDir += "/";
            }
            int prefixLen = targetDir.length();

            // 递归生成各文件的的MD5值
            ExecCommand execCommand = new ExecCommand("cd " + targetDir + " && "+"find ./ -type f -print0 | xargs -0 md5sum", new IExecStreamMonitor() {
                @Override
                public void onStart(String command) {

                }

                @Override
                public void onMsg(String line) {
                    System.out.println(line);
//                    String[] ss = line.split("  ");
//                    if(ss.length == 2){
//                        list.add(new String[]{ss[1].substring(prefixLen), ss[0]});
//                    }

                }

                @Override
                public void onEnd(int exitValue) {

                }
            });

            sshConnection.exec(execCommand);

        }, true);

        for (String[] ss : list) {
            System.out.println(StringUtils.join(ss, " "));
        }
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


//        SSHSession s1 = new SSHSession("47.100.63.232", 22, "root", "");
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