package com.automate.environment.jdk;

import com.automate.ssh.ISSHProxy;
import com.automate.ssh.SSHProxyImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/30 18:01
 */
public class JdkLinuxInstallerTest {


    public static void main(String[] args) throws Exception {

        ISSHProxy isshProxy = new SSHProxyImpl("192.168.1.111", 22, "root", "genx@linux");

        isshProxy.uploadLocalFileToRemote("K:/迅雷下载/jdk-8u191-linux-x64.tar.gz", "/usr/local");

        isshProxy.execCommand("tar zxvf /usr/local/jdk-8u191-linux-x64.tar.gz -C /usr/local");

        //修改 securerandom.source
        // java 随机数 /dev/random  会造成tomcat 启动慢
        List<String> list= isshProxy.readRemoteFileLines("/usr/local/jdk1.8.0_191/jre/lib/security/java.security");
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            if("securerandom.source=file:/dev/random".equals(s)){
                sb.append("#securerandom.source=file:/dev/random").append("\r\n");
                sb.append("securerandom.source=file:/dev/urandom").append("\r\n");
            } else {
                sb.append(s).append("\r\n");
            }
        }
        isshProxy.uploadToRemote(new ByteArrayInputStream(sb.toString().getBytes()), "/usr/local/jdk1.8.0_191/jre/lib/security/java.security");




        isshProxy.close();

    }
}