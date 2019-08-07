package com.automate.common.utils;

import com.automate.vo.PathSha1Info;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/5 20:44
 */
public class FileListSha1UtilTest {

    public static void main(String[] args) throws IOException {


        List<PathSha1Info> list = FileListSha1Util.list(new File("E:/cpcp_cm_last.apk"));

        for (PathSha1Info pathMd5Info : list) {
            System.out.println(pathMd5Info.getPath() + "\t" + pathMd5Info.getSha1() + "\t" + pathMd5Info.getSize());
        }

    }

}