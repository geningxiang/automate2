package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.common.file.FileInfo;
import com.github.gnx.automate.exec.DefaultMsgListener;
import com.github.gnx.automate.exec.ExecWorker;
import com.github.gnx.automate.exec.IExecConnection;
import com.github.gnx.automate.exec.ssh.SSHConnection;
import com.github.gnx.automate.exec.ssh.SSHExecTemplate;
import com.github.gnx.automate.exec.ssh.SSHUtil;
import com.github.gnx.automate.service.IContainerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 15:40
 */
//@SpringBootTest
class ContainerServiceImplTest {

    @Autowired
    private IContainerService containerService;

    @Test
    public void test() throws Exception {

        containerService.update(13, 5, new DefaultMsgListener());

        System.out.println("## end ##");


    }

    @Test
    public void compare() throws Exception {

        SSHExecTemplate sshExecTemplate = new SSHExecTemplate("192.168.1.190", 22, "root", "genx@linux");

        List<FileInfo> list = sshExecTemplate.execute(new ExecWorker<List<FileInfo>>() {
            @Override
            public List<FileInfo> doWork(IExecConnection execConnection) throws Exception {
                SSHConnection sshConnection = (SSHConnection) execConnection;
                return SSHUtil.sha256sum(sshConnection, "/var/caimao-webapps/client/webroot/ROOT/");

            }
        });

        for (FileInfo fileInfo : list) {
            System.out.println(fileInfo.getPath() + "\t" + fileInfo.getDigest());
        }

    }

}