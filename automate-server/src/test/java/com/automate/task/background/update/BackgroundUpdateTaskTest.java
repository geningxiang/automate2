package com.automate.task.background.update;

import com.automate.entity.ApplicationUpdateApplyEntity;
import com.automate.service.ApplicationUpdateApplyService;
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
 * @date 2019/8/11 10:09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class BackgroundUpdateTaskTest {

    @Autowired
    private ApplicationUpdateApplyService applicationUpdateApplyService;

    @Test
    public void test() throws Exception {


        Optional<ApplicationUpdateApplyEntity> applicationUpdateApplyEntity = applicationUpdateApplyService.findById(1);


        BackgroundUpdateTask b = new BackgroundUpdateTask(applicationUpdateApplyEntity.get());

        b.run();
    }
}