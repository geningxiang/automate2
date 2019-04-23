package com.automate.common.file.compare;

import com.alibaba.fastjson.JSON;
import com.automate.common.utils.FileListMd5Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/11 23:12
 */
public class FileListMd5ComparerTest {


    public static void main(String[] args) throws IOException {

        LinkedList<FileListMd5Util.PathMd5Info> list1 = FileListMd5Util.list(new File("E:\\tools\\apache-tomcat-8.5.35"));

        LinkedList<FileListMd5Util.PathMd5Info> list2 = FileListMd5Util.list(new File("E:\\tools\\apache-tomcat-9.0.13"));



        ArrayList<String[]> lst1 = parse(list1);

        ArrayList<String[]> lst2 = parse(list2);

        long a = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            FileListMd5Comparer.compare(lst1, lst2);

        }
        long b = System.currentTimeMillis();

        System.out.println("耗时:" + (b - a));


        a = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            FileListMd5Comparer.compareByMap(lst1, lst2);

        }
        b = System.currentTimeMillis();

        System.out.println("耗时:" + (b - a));

//        for (FileListMd5Comparer.FileMd5ComparerResult result : results) {
//            System.out.println(JSON.toJSONString(result));
//        }
    }

    private static ArrayList<String[]> parse(LinkedList<FileListMd5Util.PathMd5Info> list) {
        ArrayList<String[]> result = new ArrayList<>(2048);
        for (FileListMd5Util.PathMd5Info pathMd5Info : list) {

            result.add(new String[]{pathMd5Info.getPath(), pathMd5Info.getMd5()});
        }
        return result;
    }
}