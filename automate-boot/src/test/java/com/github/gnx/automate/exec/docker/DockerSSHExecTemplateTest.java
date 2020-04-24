package com.github.gnx.automate.exec.docker;

import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.common.utils.TarUtils;
import com.github.gnx.automate.exec.IExecConnection;
import com.github.gnx.automate.exec.MsgPrintListener;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/20 22:54
 */
public class DockerSSHExecTemplateTest {

    public static void main(String[] args) throws Exception {
        long a = System.currentTimeMillis();
        DockerSSHExecTemplate dockerExecTemplate = new DockerSSHExecTemplate("192.168.1.190", 2375, "mycentos:1.0.0");


//        Integer result = dockerExecTemplate.execute(new ExecWorker<>() {
//            @Override
//            public Integer doWork(IExecConnection execConnection) throws Exception {
//                return execConnection.exec("yum install -y iputils && ping -w 10 www.baidu.com", new MsgPrintListener());
//            }
//        });
//        System.out.println("result:" + result);


        IExecConnection execConnection = dockerExecTemplate.createConnection();

        IMsgListener execListener = new MsgPrintListener();



        File dir = new File("E:\\automate-data\\sourcecode\\6");
        execListener.appendLine("打包源码: " + dir.getAbsolutePath());
        File tarGzFile = TarUtils.tarAndGz(dir, dir, "tmp", true);


        String remoteDir = "/tmp/workspace";

        execListener.appendLine("上传源码 to " + remoteDir);

        execConnection.upload(tarGzFile, remoteDir, true, execListener);

        tarGzFile.deleteOnExit();


//        int result = execConnection.exec("cd "+remoteDir+"/ && mvn package -Dmaven.test.skip=true", execListener);

        int result = execConnection.exec("ipconfig", execListener);

        System.out.println(result);


//        execConnection.download("/tmp/workspace/target/caimao-ctp-0.0.1-SNAPSHOT.jar", new File("D:/temp/jar/"), execListener);
//
//        execConnection.download("/tmp/workspace/target", new File("D:/temp/target/"), execListener);



        long b = System.currentTimeMillis();

        System.out.println("共耗时:" + (b-a));


        /*
        共耗时:207206
         */
    }



}