package com.automate.service;

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
 * @date: 2019/4/14 22:50
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class ApplicationUpdateApplyServiceTest {

    @Autowired
    private ApplicationUpdateApplyService applicationUpdateApplyService;

    @Test
    public void apply() throws Exception {

        applicationUpdateApplyService.apply(20, new int[]{1});
    }
}