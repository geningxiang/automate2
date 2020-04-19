package com.github.gnx.automate.field;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author genx
 * @date 2019/6/7 19:28
 */
public class PathSha256Info {

    private static final String BACKSLASH = "\\";
    private static final String SLASH = "/";


    private String path;
    private String sha256;
    private long size;

    public PathSha256Info(String path, String sha256, long size) {
        if (BACKSLASH.equals(File.separator)) {
            //window环境下的特殊处理    \\ 转 /
            this.path = path.replace(BACKSLASH, SLASH);
        } else {
            this.path = path;
        }

        this.sha256 = sha256;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public String getSha256() {
        return sha256;
    }

    public long getSize() {
        return size;
    }
}