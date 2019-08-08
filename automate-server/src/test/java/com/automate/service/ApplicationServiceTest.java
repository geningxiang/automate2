package com.automate.service;

import com.automate.entity.ApplicationEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/8 21:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;


    @Test
    public void test() throws Exception {

        Optional<ApplicationEntity> applicationEntity = applicationService.findById(1);

        List<String[]> list = applicationService.fileSha256List(applicationEntity.get());

        for (String[] ss : list) {
            System.out.println(StringUtils.join(ss, "\t"));
        }
    }

}