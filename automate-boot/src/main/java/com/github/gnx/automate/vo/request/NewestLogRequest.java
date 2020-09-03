package com.github.gnx.automate.vo.request;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/3 22:21
 */
public class NewestLogRequest {

    private String key;

    /**
     * 已读取的长度
     */
    private int read;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getRead() {
        return read;
    }

    public void setRead(Integer read) {
        if(read != null) {
            this.read = read;
        }
    }
}
