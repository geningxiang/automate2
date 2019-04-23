package com.automate.test;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 13:34
 */
public class DiskSpaceDetail {

    /**
     * 获取磁盘信息
     * @return Map<String ,   String>: key:磁盘盘符, value:磁盘剩余空间
     */
    public static Map<String, String> getHdInfo() {

        Map<String, String> map = new TreeMap<String, String>();

        File[] roots = File.listRoots();
        double unit = Math.pow(1024, 3);

        for (int i = 0; i < roots.length; i++) {

            String hd = roots[i].getPath();

            double freespace = roots[i].getFreeSpace() / unit;

            freespace = Math.ceil((freespace * 10)) / 10;

            map.put(hd, String.valueOf(freespace));
        }

        return map;
    }

    /**
     * 啦啦啦
     * @param args
     */
    public static void main(String[] args) {

        System.out.println(getHdInfo().entrySet());
    }
}