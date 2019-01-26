package com.automate.service;

import com.automate.entity.ProjectEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void getList() {
    }

    @Test
    public void save() {
        ProjectEntity model = new ProjectEntity();
        model.setType(ProjectEntity.Type.WEB);
        model.setName("webui");
        model.setIcon("https://avatars0.githubusercontent.com/u/14215142?s=460&v=4");
        model.setRemark("测试用");
        model.setVersionType(ProjectEntity.VersionType.GIT);
        model.setVersionUrl("http://60.190.13.162:6104/genx/webui.git");
        model.setVersionUser("genx");
        model.setVersionPwd("ge10111011");
        model.setCompileType(ProjectEntity.CompileType.MAVEN);
        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        model.setStatus(ProjectEntity.Status.ACTIVATE);

        projectService.save(model);
    }
}