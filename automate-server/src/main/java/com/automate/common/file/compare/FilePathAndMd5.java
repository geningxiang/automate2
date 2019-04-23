package com.automate.common.file.compare;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/11 22:40
 */
public class FilePathAndMd5 {

    private static final String BACKSLASH = "\\";
    private static final String SLASH = "/";

    private String path;
    private String md5;
    private long size;

    public FilePathAndMd5(String path, String md5) {
        // -1 代表未知  linux md5sum 返回没有带文件大小
        this(path, md5, -1);
    }

    public FilePathAndMd5(String path, String md5, long size) {
        //如果在window 需要统一格式  使用 正斜杠
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
