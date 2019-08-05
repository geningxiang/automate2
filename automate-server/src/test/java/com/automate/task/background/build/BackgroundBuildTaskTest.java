package com.automate.task.background.build;

import com.automate.entity.AssemblyLineEntity;
import com.automate.service.AssemblyLineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/5 21:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class BackgroundBuildTaskTest {

    @Autowired
    private AssemblyLineService assemblyLineService;

    @Test
    public void test() throws Exception {

        Optional<AssemblyLineEntity> model = assemblyLineService.getModel(1);

        BackgroundBuildTask backgroundBuildTask = new BackgroundBuildTask(model.get(), 4,"", "");


        backgroundBuildTask.run();
    }

}