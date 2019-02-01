package com.automate.test;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public class DiskSpaceDetail {

    /**
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

    public static void main(String[] args) {

        System.out.println(getHdInfo().entrySet());
    }
}