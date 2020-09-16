package com.automate.ssh;

import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import com.jcraft.jsch.ChannelSftp;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/10/17 15:11
 */
public class IoxTest {

    private ChannelSftp sftp = null;

    @Test
    public void test() throws Exception {



        SSHSession s = new SSHSession("192.168.1.7", 6122, "jay", "caimao");
        s.doWork(sshConnection -> {
            // 递归生成各文件的的MD5值
//            ExecCommand execCommand = new ExecCommand("/usr/local/bin/mobileprovision-read -f \"/Users/jay/Desktop/bkxInfo/useAccount3_hoc.mobileprovision\" -o ProvisionedDevices", new ExecStreamPrintMonitor());
//
//            sshConnection.exec(execCommand);

    ///Users/jay/.rvm/rubies/ruby-2.6.0/bin/

            StringBuilder cmd = new StringBuilder(10240);
            cmd.append("export LC_ALL=en_US.UTF-8;");
            cmd.append("export LANG=en_US.UTF-8;");
            cmd.append("export PATH=/Users/jay/.rvm/rubies/ruby-2.6.0/bin:$PATH;");
            cmd.append("export FASTLANE_APPLE_APPLICATION_SPECIFIC_PASSWORD=xsde-rvaj-zmym-lqij;");
            cmd.append("export FASTLANE_SESSION=---\\n- !ruby/object:HTTP::Cookie\\n  name: DES5066f77d06ccb9698010d0aef178b6329\\n  value: HSARMTKNSRVXWFlaxHn8C7a5iieEhkSzx+ELxup6aPpSY5l/fhscXbOosS0GwXLwHaqILQ6Plveh8WdcxrwTy7sb1A/qXJ3fQZoKxUISNv43incO39AKYIOidVxEhzUmhU0WbaA=SRVX\\n  domain: idmsa.apple.com\\n  for_domain: true\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: 2592000\\n  created_at: &1 2019-11-20 11:31:17.620843000 +08:00\\n  accessed_at: *1\\n- !ruby/object:HTTP::Cookie\\n  name: myacinfo\\n  value: DAWTKNV25364c34685b9f7e1069e8d77342b85b6ed143b17fc48a4334320a03c693972f1141ce0ad0a0e5d7e32f50dad53534825100154aee70053bc25b91ac030bfdbfbe744df3dd4c11c65a649cf0364eab9c5b0e8a383ac5be53f0eecf11cb892566a02b6eef1d8cdaba69e37d2ef7c59aff2d758ae45e8b02bae377f174758eecbf9cb63f8b0a5d64527eeedbca4c6fcc615fbb6ea84dbbf584039cafd479f0e3de09239e6a0f26e97db071a44f8014f5bfdac3c011dcb3ed982b487b0adb4fe1612c6c406ad2ca6a4db178efdf0c7e85daf5675e4e863b7414dc8a2bbb434eaac035801a0d1df067545b2734129b903430d7644def5a5ea87012b8e5ad201c0424a0892f1cf63b39c3b8f2dd7f5b9e16ec1c173c57f8abf34404ba6ac58f074328a5fa7b679a9c58d9d1ab08eee8341d9f364373836636435623966616665396266316432393264363263663335656331616439386562393663MVRYV2\\n  domain: apple.com\\n  for_domain: true\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: \\n  created_at: 2019-11-20 11:31:17.620993000 +08:00\\n  accessed_at: 2019-11-20 11:31:17.634095000 +08:00\\n- !ruby/object:HTTP::Cookie\\n  name: dqsid\\n  value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NzQyMjA2NzcsImp0aSI6IndMdUJVVUE0VDFFVEdBZXFIVTd6Y3cifQ.cKLncFxXJVbaI625h7rsiic2nSHhqk82nMFnZYXrQ04\\n  domain: appstoreconnect.apple.com\\n  for_domain: false\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: 1799\\n  created_at: &2 2019-11-20 11:31:18.340060000 +08:00\\n  accessed_at: *2\\n;");


            cmd.append("cd /Users/jay/Desktop/bkx;");
//
            cmd.append("fastlane useAccount3 signingIdentity:\"iPhone Distribution: zhejiang xuanxin information  Science and Technology Ltd. (ENH6LAME5K)\" deviceName:\"wjcyk\" deviceUDID:\"af5a808e08cdf8a96eeabdc0d426d6eb0c3d5c14\" outputFilePath:\"/Users/jay/Desktop/bkxInfo\" outputPPname:\"useAccount3_hoc.mobileprovision\" outputPPPath:\"/Users/jay/Desktop/bkxInfo/useAccount3_hoc.mobileprovision\" inputIPApath:\"/Users/jay/Desktop/bkxInfo/useAccount3.ipa\"");
            /*cmd.append("./run.sh");*/

            //Users/jay/Desktop/bkx
            ExecCommand execCommand = new ExecCommand(cmd.toString(), new ExecStreamPrintMonitor());


            execCommand.setTimeOut(TimeUnit.MINUTES, 10);


            sshConnection.exec(execCommand);


            execCommand.getExitValue();
        }, true);
    }

    @Test
    public void testDownlaoad(){
        StringBuilder cmd = new StringBuilder(10240);
        cmd.append("---\\n- !ruby/object:HTTP::Cookie\\n  name: DES5066f77d06ccb9698010d0aef178b6329\\n  value: HSARMTKNSRVXWFlaxHn8C7a5iieEhkSzx+ELxup6aPpSY5l/fhscXbOosS0GwXLwHaqILQ6Plveh8WdcxrwTy7sb1A/qXJ3fQZoKxUISNv43incO39AKYIOidVxEhzUmhU0WbaA=SRVX\\n  domain: idmsa.apple.com\\n  for_domain: true\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: 2592000\\n  created_at: &1 2019-11-20 11:31:17.620843000 +08:00\\n  accessed_at: *1\\n- !ruby/object:HTTP::Cookie\\n  name: myacinfo\\n  value: DAWTKNV25364c34685b9f7e1069e8d77342b85b6ed143b17fc48a4334320a03c693972f1141ce0ad0a0e5d7e32f50dad53534825100154aee70053bc25b91ac030bfdbfbe744df3dd4c11c65a649cf0364eab9c5b0e8a383ac5be53f0eecf11cb892566a02b6eef1d8cdaba69e37d2ef7c59aff2d758ae45e8b02bae377f174758eecbf9cb63f8b0a5d64527eeedbca4c6fcc615fbb6ea84dbbf584039cafd479f0e3de09239e6a0f26e97db071a44f8014f5bfdac3c011dcb3ed982b487b0adb4fe1612c6c406ad2ca6a4db178efdf0c7e85daf5675e4e863b7414dc8a2bbb434eaac035801a0d1df067545b2734129b903430d7644def5a5ea87012b8e5ad201c0424a0892f1cf63b39c3b8f2dd7f5b9e16ec1c173c57f8abf34404ba6ac58f074328a5fa7b679a9c58d9d1ab08eee8341d9f364373836636435623966616665396266316432393264363263663335656331616439386562393663MVRYV2\\n  domain: apple.com\\n  for_domain: true\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: \\n  created_at: 2019-11-20 11:31:17.620993000 +08:00\\n  accessed_at: 2019-11-20 11:31:17.634095000 +08:00\\n- !ruby/object:HTTP::Cookie\\n  name: dqsid\\n  value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NzQyMjA2NzcsImp0aSI6IndMdUJVVUE0VDFFVEdBZXFIVTd6Y3cifQ.cKLncFxXJVbaI625h7rsiic2nSHhqk82nMFnZYXrQ04\\n  domain: appstoreconnect.apple.com\\n  for_domain: false\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: 1799\\n  created_at: &2 2019-11-20 11:31:18.340060000 +08:00\\n  accessed_at: *2\\n");
        System.out.println(cmd);
    }



}
