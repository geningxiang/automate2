package com.github.gnx.automate.common.file;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/1 22:13
 */
public class FileInfo {

    /**
     * 路径
     */
    private final String path;

    /**
     * 加密摘要
     * 默认使用 sha256
     */
    private final String digest;

    public FileInfo(String path, String digest) {
        //      \\ 统一转  /
        this.path = path.replace("\\", "/");
        this.digest = digest;
    }

    public String getPath() {
        return path;
    }

    public String getDigest() {
        return digest;
    }
}
