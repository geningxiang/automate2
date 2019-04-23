package com.automate.service;

import com.automate.contants.CompileType;
import com.automate.contants.ProjectType;
import com.automate.contants.VcsType;
import com.automate.entity.ProjectEntity;
import com.automate.vcs.git.GitHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        model.setType(ProjectType.JAVA);
        model.setName("SpringBootDemo");
        model.setRemark("测试用");
        model.setVcsType(VcsType.GIT);
        model.setVcsUrl("http://60.190.13.162:6104/genx/SpringBootDemo.git");
        model.setCompileType(CompileType.MAVEN);
        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        model.setStatus(ProjectEntity.Status.ACTIVATE);
        projectService.save(model);
    }


    @Test
    public void init() throws Exception {
        Optional<ProjectEntity> sourceCodeEntity = projectService.getModel(1);
        projectService.init(sourceCodeEntity.get());
    }

    @Test
    public void sync() throws Exception {
        Optional<ProjectEntity> sourceCodeEntity = projectService.getModel(1);
        projectService.sync(sourceCodeEntity.get());
    }

    @Test
    public void syncWidthBranchList() throws Exception {
        Optional<ProjectEntity> sourceCodeEntity = projectService.getModel(1);
        GitHelper gitHelper = new GitHelper(sourceCodeEntity.get());
        List<String> branchList = new ArrayList<>();
        branchList.add("master");
        branchList.add("develop1");
        branchList.add("develop2");
        projectService.sync(sourceCodeEntity.get(), gitHelper, branchList);
    }
}