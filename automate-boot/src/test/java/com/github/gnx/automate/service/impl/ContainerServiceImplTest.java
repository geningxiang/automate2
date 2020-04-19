package com.github.gnx.automate.service.impl;

import com.github.gnx.automate.service.IContainerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 15:40
 */
@SpringBootTest
class ContainerServiceImplTest {

    @Autowired
    private IContainerService containerService;

    @Test
    public void test() throws Exception {

        containerService.update(6, 1, null);

        System.out.println("## end ##");


    }

}