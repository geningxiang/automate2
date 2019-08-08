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
 * @author genx
 * @date 2019/8/6 21:46
 */
public class SimHashTest {


    public static void main(String[] args) throws IOException {
        List<PathSha256Info> list1 = FileListSha256Util.list(new File("D:\\idea-workspace\\automate2\\automate-server\\target\\Automate2"));

        List<String> datas1 = new ArrayList(list1.size());
        for (PathSha256Info pathSha256Info : list1) {
            datas1.add(pathSha256Info.getPath() + ":" + pathSha256Info.getSha256());
        }

        SimHash simHash1 = new SimHash(datas1, 64);

        System.out.println(simHash1.simHash().toString(2));
        System.out.println(simHash1.simHash());


        List<PathSha256Info> list2 = FileListSha256Util.list(new File("D:\\idea-workspace\\automate2\\automate-server\\target\\Automate2 - 副本"));

        List<String> datas2 = new ArrayList(list2.size());
        for (PathSha256Info pathSha256Info : list2) {
            datas2.add(pathSha256Info.getPath() + ":" + pathSha256Info.getSha256());
        }

        SimHash simHash2 = new SimHash(datas2, 64);

        System.out.println(simHash2.simHash().toString(2));
        System.out.println(simHash2.simHash());

        System.out.println(simHash1.hammingDistance(simHash2));


    }

}