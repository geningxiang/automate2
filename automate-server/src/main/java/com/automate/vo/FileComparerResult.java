package com.automate.vo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author genx
 * @date 2019/6/7 19:41
 */
public class FileComparerResult {
    /**
     * 路径
     */
    private String path;

    /**
     * 数组   md5 或者 sha1
     */
    private String[] codes;

    /**
     * 是否全部一致
     */
    private boolean uniformity = false;

    public FileComparerResult(int count) {
        this.codes = new String[count];
    }

    public String getPath() {
        return path;
    }

    public String[] getCodes() {
        return codes;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCode(int index, String code) {
        this.codes[index] = code;
    }

    public boolean isUniformity() {
        return uniformity;
    }

    public void setUniformity(boolean uniformity) {
        this.uniformity = uniformity;
    }
}