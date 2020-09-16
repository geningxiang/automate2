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
    public void download()throws Exception{
        SSHSession s = new SSHSession("192.168.1.7", 6122, "jay", "caimao");

        s.doWork(sshConnection -> {
            String localFile="I:/test/test.ipa";
            String remoteDir="/Users/jay/Desktop/bkxInfo/";
            String remoteName="useAccount3.ipa";

            sshConnection.downloadRemoteFileToLocal(remoteDir,remoteName,localFile);
        },true);
    }

    @Test
    public void editFile() throws Exception {
        SSHSession s = new SSHSession("192.168.1.7", 6122, "jay", "caimao");

        s.doWork(sshConnection -> {
            ExecCommand execCommand = new ExecCommand(Arrays.asList(
                    "export " + "FASTLANE_APPLE_APPLICATION_SPECIFIC_PASSWORD=uluw-cbxv-ppnt-qzdd",
                    "export " + "FASTLANE_SESSION='---\\n- !ruby/object:HTTP::Cookie\\n  name: DES5066f77d06ccb9698010d0aef178b6329\\n  value: HSARMTKNSRVXWFlaTnf6RIQrf8uJ4loBXGShFDGLteRmRWVnLfndltsvk8nGYsU0gMaSAxN6YF6IkmIGZ5hJSO1AzE+swr7oUXxVN7lC07ShkmPZT//9k+ycuqZfNHD74poi+hw=SRVX\\n  domain: idmsa.apple.com\\n  for_domain: true\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: 2592000\\n  created_at: 2019-10-15 15:36:37.079813000 +08:00\\n  accessed_at: 2019-10-18 09:13:52.657008000 +08:00\\n- !ruby/object:HTTP::Cookie\\n  name: myacinfo\\n  value: DAWTKNV22a5249d14397689d813c2150efc972e70a0518fe976b84068014882e997691ff831f686e2f9fee7c8f96d10504463375f0811a77ec6035166164efd504cadad0cbf1c63dbd551bee5f01b4c7708dcd394a9c59bfe328f3397e52cb01a9dcde9367bad8e6ba8ed117b597fd6bfd32a279537c7411f5bf65666b8a59c697b3e39267044e37c3600701f2dea3e7234b23fd70f1431685ea94f43a55f6035510525700b762dc648e9d2b69675696cd88618bca0c5dd31fb9b941485bbdfcb0f1fff81b3234a5fe9d362d7741bc2a34815dcf10ce623338052187a1bb7c44959b14e3803628c20ac8cd240586739e587569eb1b55d245d1391350bcb2c0fbfd2f3f8d65396130383535623636373765323531656235663839323566336164343830616634386434383836MVRYV2\\n  domain: apple.com\\n  for_domain: true\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: \\n  created_at: 2019-10-18 09:13:54.571905000 +08:00\\n  accessed_at: 2019-10-18 09:13:54.572814000 +08:00\\n- !ruby/object:HTTP::Cookie\\n  name: dqsid\\n  value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NzEzNjEyMzQsImp0aSI6IlI2TUhZUHNraGJRMWZLN1BIVEJ4NXcifQ.MG5fTpycSfoZVFWhA7_kcqxNgPjmzX326gaIyy_Q_4M\\n  domain: appstoreconnect.apple.com\\n  for_domain: false\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: 1799\\n  created_at: &1 2019-10-18 09:13:55.253952000 +08:00\\n  accessed_at: *1\\n'\n"
            ), new ExecStreamPrintMonitor()
            );

            sshConnection.exec(execCommand);
        },true);
    }

    @Test
    public void md5sum() throws Exception {
        SSHSession s = new SSHSession("47.100.63.232", 22, "root", "Genx@linux");

        List<String[]> list = new ArrayList(1024);
        s.doWork(sshConnection -> {

            String targetDir = "/www/automate-temp2/webapps/ROOT/";
            if (!targetDir.endsWith("/")) {
                targetDir += "/";
            }
            int prefixLen = targetDir.length();

            // 递归生成各文件的的MD5值
            ExecCommand execCommand = new ExecCommand("cd " + targetDir + " && " + "find ./ -type f -print0 | xargs -0 md5sum", new IExecStreamMonitor() {
                @Override
                public void onStart(String command) {

                }

                @Override
                public void onMsg(String line) {
                    System.out.println(line);

                }

                @Override
                public void onError(String line) {
                    System.err.println(line);
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