package com.github.gnx.automate.common.file;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/8/1 23:03
 */
public class FileCompareResult {

    /**
     * 需要比对的数量
     */
    private int sourceCount;

    /**
     * 来源信息
     */
    private String[] source;

    /**
     * 一致的数量
     */
    private int fitCount;

    /**
     * 不一致的数量
     */
    private int diffCount;


    /**
     * 文件信息合并结果
     * [文件path, 文件源1加密摘要, 文件源2加密摘要, ...]
     */
    private List<String[]> data;


    public int getSourceCount() {
        return sourceCount;
    }

    public void setSourceCount(int sourceCount) {
        this.sourceCount = sourceCount;
    }

    public String[] getSource() {
        return source;
    }

    public void setSource(String[] source) {
        this.source = source;
    }

    public int getFitCount() {
        return fitCount;
    }

    public void setFitCount(int fitCount) {
        this.fitCount = fitCount;
    }

    public int getDiffCount() {
        return diffCount;
    }

    public void setDiffCount(int diffCount) {
        this.diffCount = diffCount;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }
}
