package com.automate.task.background.assembly.impl;

import com.automate.common.SystemConfig;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/5 21:54
 */
public class PackageExtractAssemblyStepTaskTest {
    private static final String BASE_DIR = "${baseDir}";


    public static void main(String[] args) {
        String path = "${baseDir}/automate-web/target/Automate2.war";
        if(path.contains(BASE_DIR)){
           path = path.replace(BASE_DIR, "D:/idea-workspace/Automate2/");
        }

        System.out.println(path);
        File file = new File(path);
        System.out.println(file.getAbsolutePath());

        if(file.exists()){
            if(file.isDirectory()){
                System.out.println("这是个文件夹,准备做打包处理");
            } else {
                System.out.println("这是个文件,后缀是" + file.getName().substring(file.getName().lastIndexOf(".") + 1));
            }
        }
    }
}