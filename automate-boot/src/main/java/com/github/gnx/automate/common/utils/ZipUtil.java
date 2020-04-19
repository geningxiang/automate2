package com.github.gnx.automate.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/8 23:29
 */
public class ZipUtil {

    public static void compress(File sourceFile, File destFile) throws IOException {
        //创建zip输出流
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(destFile));
            //调用函数
            compress(out, sourceFile, "");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static void compress(ZipOutputStream out, File sourceFile, String basePath) throws IOException {
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] flist = sourceFile.listFiles();

            if (flist.length == 0) {
                //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
                out.putNextEntry(new ZipEntry(basePath + "/"));
            } else {
                //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                for (int i = 0; i < flist.length; i++) {
                    compress(out, flist[i], (StringUtils.isNotBlank(basePath) ? basePath + "/" : "") + flist[i].getName());
                }
            }
        } else {
            //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
            out.putNextEntry(new ZipEntry((StringUtils.isNotBlank(basePath) ? basePath + "/" : "") + sourceFile.getName()));
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            byte[] temp = new byte[1024];
            int read;
            //将源文件写入到zip文件中
            while ((read = bis.read(temp)) != -1) {
                out.write(temp, 0, read);
            }
            bis.close();
        }
    }

}
