package com.automate.ssh;

import com.automate.exec.ExecCommand;
import com.automate.exec.ExecStreamPrintMonitor;
import com.automate.exec.IExecStreamMonitor;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/10/17 15:11
 */
public class IoxTest {

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
            cmd.append("export PATH=/Users/jay/.rvm/rubies/ruby-2.6.0/bin:$PATH;");
//            cmd.append("export LC_ALL=en_US.UTF-8;");
//            cmd.append("export LANG=en_US.UTF-8;");

            cmd.append("export FASTLANE_APPLE_APPLICATION_SPECIFIC_PASSWORD=uluw-cbxv-ppnt-qzid;");
            cmd.append("export FASTLANE_SESSION='---\\n- !ruby/object:HTTP::Cookie\\n  name: DES5066f77d06ccb9698010d0aef178b6329\\n  value: HSARMTKNSRVXWFlaTnf6RIQrf8uJ4loBXGShFDGLteRmRWVnLfndltsvk8nGYsU0gMaSAxN6YF6IkmIGZ5hJSO1AzE+swr7oUXxVN7lC07ShkmPZT//9k+ycuqZfNHD74poi+hw=SRVX\\n  domain: idmsa.apple.com\\n  for_domain: true\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: 2592000\\n  created_at: 2019-10-15 15:36:37.079813000 +08:00\\n  accessed_at: 2019-10-17 08:51:35.475211000 +08:00\\n- !ruby/object:HTTP::Cookie\\n  name: myacinfo\\n  value: DAWTKNV2c4480da5595bec84191e123d12800ab2c95f5616112ae060d4ed2873f101b369c4e7a6a7f595621aca74e0e7a54aa834483206e65314187c1451361b94f72bf0474283e3f538500d3b2ba3ab5d21b17e4cc20bb81949d8694772169007093bb55c34ebf245931d126e83e6d307eb31fdbd4ec5f7282975dbe89f946b1d531dd1fe5e931783002feee41af0b364ee21b9930b5d930fb1e874c8d4cf2bbe313bbddfc93d65af4bebdae793ec21b018b9dc1b278c716b1de624b55231c76e20af7382f7fbe8b1a44d2905e61e395a147a35bf8fcdbb0be02241cfa16f471a633d4b23228e56a6314461eea50c65b918edacf506afb71a64b145f55ffae626c3d84265646439643261633031643866316564333461333166373365353830663133346365356536396633MVRYV2\\n  domain: apple.com\\n  for_domain: true\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: \\n  created_at: 2019-10-17 08:51:36.421410000 +08:00\\n  accessed_at: 2019-10-17 08:51:36.422163000 +08:00\\n- !ruby/object:HTTP::Cookie\\n  name: dqsid\\n  value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NzEyNzM0OTYsImp0aSI6InJubVlYdksxa2JFV1A2MjVsM09LTFEifQ.ZRLWLgEk8ijnDkP8bPjqJ2WOylGNQBWXoQiVHz378L8\\n  domain: appstoreconnect.apple.com\\n  for_domain: false\\n  path: \"/\"\\n  secure: true\\n  httponly: true\\n  expires: \\n  max_age: 1799\\n  created_at: &1 2019-10-17 08:51:36.920720000 +08:00\\n  accessed_at: *1\\n';");
            cmd.append("cd /Users/jay/Desktop/bkx;");
//            cmd.append("fastlane env;");

//
//            cmd.append("fastlane useAccount3 signingIdentity:\"iPhone Distribution: zhejiang xuanxin information  Science and Technology Ltd. (ENH6LAME5K)\" deviceName:\"wjcyk\" deviceUDID:\"af5a808e08cdf8a96eeabdc0d426d6eb0c3d5c14\" outputFilePath:\"/Users/jay/Desktop/bkxInfo\" outputPPname:\"useAccount3_hoc.mobileprovision\" outputPPPath:\"/Users/jay/Desktop/bkxInfo/useAccount3_hoc.mobileprovision\" inputIPApath:\"/Users/jay/Desktop/bkxInfo/useAccount3.ipa\"");
            cmd.append("./run.sh");

            //Users/jay/Desktop/bkx
            ExecCommand execCommand = new ExecCommand(cmd.toString(), new ExecStreamPrintMonitor());


            execCommand.setTimeOut(TimeUnit.MINUTES, 10);


            sshConnection.exec(execCommand);

        }, true);
    }

}
