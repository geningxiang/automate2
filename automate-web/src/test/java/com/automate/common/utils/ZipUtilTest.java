package com.automate.common.utils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/8 23:41
 */
public class ZipUtilTest {

    public static void main(String[] args) throws IOException {
        File dir = new File("E:\\work\\automate2.sh");

        File destFile = new File("E:/work/1.zip");

        ZipUtil.compress(dir, destFile);
    }

}