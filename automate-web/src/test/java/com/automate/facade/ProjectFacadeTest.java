package com.automate.facade;

import com.automate.entity.ProjectEntity;
import com.automate.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 0:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class ProjectFacadeTest {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectFacade projectFacade;

    @Test
    public void init() throws Exception {
        Optional<ProjectEntity> projectEntity = projectService.getModel(1);

        projectFacade.init(projectEntity.get());
    }

    @Test
    public void sync() throws Exception {
        Optional<ProjectEntity> projectEntity = projectService.getModel(1);

        projectFacade.sync(projectEntity.get());
    }

}