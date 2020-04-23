package com.github.gnx.automate.exec.ssh;

import com.github.gnx.automate.common.IExecListener;
import com.github.gnx.automate.common.utils.TarUtils;
import com.github.gnx.automate.exec.ExecPrintListener;
import com.github.gnx.automate.exec.ExecWorker;
import com.github.gnx.automate.exec.IExecConnection;
import com.github.gnx.automate.exec.IExecTemplate;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/23 22:58
 */
class SSHExecTemplateTest {


    public static void main(String[] args) throws Exception {

        IExecTemplate execTemplate = new SSHExecTemplate("192.168.1.190", 22, "root", "genx@linux");

        execTemplate.execute(new ExecWorker<Object>() {
            @Override
            public Object doWork(IExecConnection execConnection) throws Exception {
                //上传源码
                File dir = new File("E:\\automate-data\\sourcecode\\1");
                File tarGzFile = TarUtils.tarAndGz(dir, dir, "tmp", true);

                IExecListener execListener = new ExecPrintListener();

                execConnection.upload(tarGzFile, "/tmp/CaimaoTouch", true, execListener);

//                execConnection.exec("cd /tmp/CaimaoTouch && tar -zxvf " + tarGzFile.getName() + " && rm " + tarGzFile.getName(), execListener);

                tarGzFile.deleteOnExit();

                return null;
            }
        });


    }
}