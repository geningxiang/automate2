package com.automate.service;

import com.automate.entity.HookLogEntity;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 22:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class HookLogServiceTest {

    @Autowired
    private HookLogService hookLogService;

    @Test
    public void findAll() {
        Page<HookLogEntity> page = hookLogService.findAll(PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("total=" + page.getTotalElements());

        for (HookLogEntity hookLogEntity : page) {
            System.out.println(hookLogEntity.getId());
        }
    }


    @Test
    public void getModel() {
    }
}