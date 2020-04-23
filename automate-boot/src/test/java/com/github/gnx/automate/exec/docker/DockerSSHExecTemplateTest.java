package com.github.gnx.automate.exec.docker;

import com.github.gnx.automate.common.IExecListener;
import com.github.gnx.automate.common.utils.TarUtils;
import com.github.gnx.automate.exec.ExecPrintListener;
import com.github.gnx.automate.exec.IExecConnection;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/20 22:54
 */
public class DockerSSHExecTemplateTest {

    public static void main(String[] args) throws Exception {
        DockerSSHExecTemplate dockerExecTemplate = new DockerSSHExecTemplate("192.168.1.190", 2375, "mycentos:1.0.0");


//        Integer result = dockerExecTemplate.execute(new ExecWorker<>() {
//            @Override
//            public Integer doWork(IExecConnection execConnection) throws Exception {
//                return execConnection.exec("yum install -y iputils && ping -w 10 www.baidu.com", new ExecPrintListener());
//            }
//        });
//        System.out.println("result:" + result);


        IExecConnection execConnection = dockerExecTemplate.createConnection();


        File dir = new File("E:\\automate-data\\sourcecode\\1");
        File tarGzFile = TarUtils.tarAndGz(dir, dir, "tmp", true);

        IExecListener execListener = new ExecPrintListener();

        execConnection.upload(tarGzFile, "/tmp/CaimaoTouch", true, execListener);

        tarGzFile.deleteOnExit();


    }

}