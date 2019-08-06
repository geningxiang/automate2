package com.automate.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.automate.common.file.compare.FileListComparer;
import com.automate.common.utils.FileListSha1Util;
import com.automate.entity.ProjectPackageEntity;
import com.automate.vo.FileComparerResult;
import com.automate.vo.PathSha1Info;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/5/26 20:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class ProjectPackageServiceTest {

    @Autowired
    private ProjectPackageService projectPackageService;

    @Test
    public void findAll() {

    }

    @Test
    public void create() throws IOException {

        projectPackageService.create(1, "2.0.0", "master", "", "测试的", new File("D:\\idea-workspace\\automate2\\automate-server\\target\\Automate2.war"), ProjectPackageEntity.Type.WHOLE, 0);

        projectPackageService.create(1, "2.0.1", "master", "", "测试的", new File("D:\\idea-workspace\\automate2\\automate-server\\target\\Automate2"), ProjectPackageEntity.Type.WHOLE, 0);

    }

    @Test
    public void getFirstBySha1OrderByIdDesc() {

        ProjectPackageEntity model = projectPackageService.getFirstByFileSha1OrderByIdDesc("823a84ec14149e34ff28af79a47a519e080d409e");

        System.out.println(JSON.toJSONString(model));

    }

    @Test
    public void test() {

        ProjectPackageEntity model1 = projectPackageService.getFirstByFileSha1OrderByIdDesc("68339599f2322155473302cdb174a21e48bfcae4");


        List<PathSha1Info> list1 = JSONArray.parseArray(model1.getFileList(), PathSha1Info.class);


        ProjectPackageEntity model2 = projectPackageService.getFirstByFileSha1OrderByIdDesc("467356a642c4c59ec6a3103bfa707a4209282493");

        List<PathSha1Info> list2 = JSONArray.parseArray(model2.getFileList(), PathSha1Info.class);

        LinkedList<FileComparerResult> results = FileListComparer.compare(list1, list2);

        for (FileComparerResult result : results) {
            if (!result.isUniformity()) {
                System.out.println(result.getPath());
            }
        }
    }

    @Test
    public void sha1() throws IOException {
        String a = DigestUtils.sha1Hex(new FileInputStream("E:\\automate-data\\sourcecode\\4\\automate-web\\target/Automate2-1.zip"));
        String b = DigestUtils.sha1Hex(new FileInputStream("E:\\automate-data\\sourcecode\\4\\automate-web\\target/Automate2-2.zip"));
        System.out.println(a);
        System.out.println(b);

        a = DigestUtils.md5Hex(new FileInputStream("E:\\automate-data\\sourcecode\\4\\automate-web\\target/Automate2-1.zip"));
        b = DigestUtils.md5Hex(new FileInputStream("E:\\automate-data\\sourcecode\\4\\automate-web\\target/Automate2-2.zip"));
        System.out.println(a);
        System.out.println(b);

    }




}