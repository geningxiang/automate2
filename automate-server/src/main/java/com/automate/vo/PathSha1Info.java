package com.automate.vo;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author genx
 * @date 2019/6/7 19:28
 */
public class PathSha1Info {

    private static final String BACKSLASH = "\\";
    private static final String SLASH = "/";


    private String path;
    private String sha1;
    private long size;

    public PathSha1Info(String path, String sha1, long size) {
        if (BACKSLASH.equals(File.separator)) {
            //window环境下的特殊处理    \\ 转 /
            this.path = path.replace(BACKSLASH, SLASH);
        } else {
            this.path = path;
        }

        this.sha1 = sha1;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public String getSha1() {
        return sha1;
    }

    public long getSize() {
        return size;
    }
}