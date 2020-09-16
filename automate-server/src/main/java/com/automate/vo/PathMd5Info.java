package com.automate.vo;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author genx
 * @date 2019/6/7 19:34
 */
public class PathMd5Info {

    private static final String BACKSLASH = "\\";
    private static final String SLASH = "/";

    private String path;
    private String md5;
    private long size;

    public PathMd5Info(String path, String md5, long size) {
        if (BACKSLASH.equals(File.separator)) {
            this.path = path.replace(BACKSLASH, SLASH);
        } else {
            this.path = path;
        }

        this.md5 = md5;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public String getMd5() {
        return md5;
    }

    public long getSize() {
        return size;
    }
}