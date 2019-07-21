package com.automate.service;

import com.alibaba.fastjson.JSON;
import com.automate.entity.ProjectPackageEntity;
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

        projectPackageService.create(1, "2.0.0", "master", "", "测试的", new File("D:\\idea-workspace\\automate2\\automate-server\\target\\Automate2.war"), ProjectPackageEntity.Type.WHOLE, 0);

        projectPackageService.create(1, "2.0.1", "master", "", "测试的", new File("D:\\idea-workspace\\automate2\\automate-server\\target\\Automate2"), ProjectPackageEntity.Type.WHOLE, 0);

    }

    @Test
    public void getFirstBySha1OrderByIdDesc(){

        ProjectPackageEntity model = projectPackageService.getFirstBySha1OrderByIdDesc("823a84ec14149e34ff28af79a47a519e080d409e");

        System.out.println(JSON.toJSONString(model ));

    }
}