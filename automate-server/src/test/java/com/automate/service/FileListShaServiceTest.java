package com.automate.service;

import com.automate.entity.FileListShaEntity;
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
 * @author genx
 * @date 2019/8/8 0:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class FileListShaServiceTest {

    @Autowired
    private FileListShaService fileListShaService;

    @Test
    public void findById(){
        Optional<FileListShaEntity> model = fileListShaService.findById("f9772c562da8af16e7471eb759bbf9abb2ade04f");

        System.out.println(model);
    }
}