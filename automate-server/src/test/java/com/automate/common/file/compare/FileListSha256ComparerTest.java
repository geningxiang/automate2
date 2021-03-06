package com.automate.common.file.compare;

import com.automate.common.utils.FileListSha256Util;
import com.automate.vo.PathSha256Info;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/11 23:12
 */
public class FileListSha256ComparerTest {


    public static void main(String[] args) throws IOException {

        List<PathSha256Info> list1 = FileListSha256Util.list(new File("E:\\tools\\apache-tomcat-8.5.35"));

        List<PathSha256Info> list2 = FileListSha256Util.list(new File("E:\\tools\\apache-tomcat-9.0.13"));


        ArrayList<String[]> lst1 = parse(list1);

        ArrayList<String[]> lst2 = parse(list2);

        long a = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            FileListComparer.compare(lst1, lst2);

        }
        long b = System.currentTimeMillis();

        System.out.println("耗时:" + (b - a));


        a = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            FileListComparer.compareByMap(lst1, lst2);

        }
        b = System.currentTimeMillis();

        System.out.println("耗时:" + (b - a));

//        for (FileListMd5Comparer.FileMd5ComparerResult result : results) {
//            System.out.println(JSON.toJSONString(result));
//        }
    }

    private static ArrayList<String[]> parse(List<PathSha256Info> list) {
        ArrayList<String[]> result = new ArrayList<>(2048);
        for (PathSha256Info pathMd5Info : list) {
            result.add(new String[]{pathMd5Info.getPath(), pathMd5Info.getSha256()});
        }
        return result;
    }
}