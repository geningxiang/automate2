package com.genx.auotmate.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

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

    }

    @Test
    public void update() {

    }

    @Test
    public void deleteById() {

    }
}