package com.automate.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/5/26 20:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class ProjectPackageServiceTest {

    @Autowired
    private ProjectPackageService projectPackageService;

    @Test
    public void findAll() {


    }

    @Test
    public void create() throws IOException {

        projectPackageService.create(1, "2.0.0", "master", "", new File("D:\\idea-workspace\\automate2\\automate-server\\target\\Automate2.war"), 0);

        projectPackageService.create(1, "2.0.1", "master", "", new File("D:\\idea-workspace\\automate2\\automate-server\\target\\Automate2"), 0);

    }
}