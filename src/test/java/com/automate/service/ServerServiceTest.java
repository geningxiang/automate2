package com.automate.service;

import com.automate.entity.ServerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/17 23:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml", "classpath:/spring-mvc.xml"})
public class ServerServiceTest {

    @Autowired
    private ServerService serverService;

    @Test
    public void save() {
        ServerEntity serverEntity = new ServerEntity();

        serverEntity.setType(ServerEntity.Type.LINUX.ordinal());
        serverEntity.setName("测试环境190");

        serverEntity.setInsideIp("192.168.1.190");
        serverEntity.setOutsideIp("60.190.13.62");

        serverEntity.setSshHost("60.190.13.62");
        serverEntity.setSshPort(6122);
        serverEntity.setSshUser("caimaoApp");
        serverEntity.setSshPwd("87677911");

        serverEntity.setRemark("");
        serverEntity.setEnvironment(ServerEntity.Environment.TEST.ordinal());
        serverEntity.setStatus(ServerEntity.Status.NORMAL.ordinal());

        serverService.save(serverEntity);
    }

    @Test
    public void update() {

    }

    @Test
    public void deleteById() {

    }
}