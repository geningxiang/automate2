package com.github.gnx.automate.common.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/23 21:02
 */
class TarUtilsTest {


    public static void main(String[] args) throws IOException {


        TarUtils.unTar(new File("D:/temp/target/temp.tar"), new File("D:/temp/target"));
    }

}