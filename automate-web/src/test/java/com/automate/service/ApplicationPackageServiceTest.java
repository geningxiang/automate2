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
 *
 * @author: genx
 * @date: 2019/4/5 19:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class ApplicationPackageServiceTest {

    @Autowired
    private ApplicationPackageService applicationPackageService;

    @Test
    public void create() throws IOException {

        applicationPackageService.create(5, "2.0.0", "master", "",
                new File("D:\\idea-workspace\\Automate2\\automate-web\\target/Automate2.war"), 0);

    }

}