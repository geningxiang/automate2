package com.github.gnx.automate.exec.docker;

import com.github.gnx.automate.exec.DefaultMsgListener;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/6 13:10
 */
class DockerSSHConnetctionTest {

    @Test
    public void test() throws Exception {

        DockerSSHConnetction dockerSSHConnetction = new DockerSSHConnetction("geningxiang.oicp.net", 2375, "mycentos:1.0.0");

//        DockerSSHConnetction dockerSSHConnetction = new DockerSSHConnetction("192.168.1.190", 2375, "mycentos:1.0.0");


        dockerSSHConnetction.exec("ifconfig", new DefaultMsgListener());

        dockerSSHConnetction.close();

    }

}