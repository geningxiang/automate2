package com.automate.ssh;

import com.automate.common.CmdResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/18 22:28
 */
public class SSHProxyImplTest {

    @Test
    public void execCommand() throws Exception {
        ISSHProxy isshProxy = new SSHProxyImpl("192.168.1.190", 22, "root", "genx@linux");

        CmdResult sshResult = isshProxy.execCommand("/cmd/tomcat-automate.sh start");

        System.out.println(sshResult.getExitStatus());

        System.out.println("######################");


        for (String s : sshResult.getResult()) {
            System.out.println(s);

//            //USER        PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
//            String[] ss = s.split("\\s+");
//            if(ss.length >= 11){
//                String command = ss[10];
//
//
//
//
//                String[] tt = Arrays.copyOfRange(ss, 10, ss.length);
//                System.out.println(StringUtils.join(tt, " "));
//            }
        }


        isshProxy.close();
    }

    public static void main(String[] args) {
        String s = "root       2924  0.1  1.0 358544 86112 ?        Sl   Oct22  94:59 ./gogs web";

        String[] ss = s.split("\\s+");

        System.out.println(StringUtils.join(ss, ","));
    }
}