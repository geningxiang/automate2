package com.automate.controller.other;

import com.automate.common.SystemConfig;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author: genx
 * @date: 2019/3/25 15:11
 */
public class MavenControllerTest {

    public static void main(String[] args) {
        File file = new File("D:\\Program Files\\apache-maven-3.5.3\\repository\\com\\caimao/lottery/caimao.common/maven-metadata.xml");

        System.out.println(file.getAbsolutePath());

        String s = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator) + 1);

        System.out.println(s);

    }
}