package com.github.gnx.automate.vcs.vo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *  版本控制系统的提交日志
 * @author: genx
 * @date: 2019/1/26 22:44
 */
public class CommitLog {
    /**
     * 唯一编号
     */
    private String id;
    /**
     * 提交时的时间戳
     */
    private long commitTime;

    /**
     * 作者
     */
    private String author;

    /**
     * 邮箱地址
     * git 有
     * svn 没有
     */
    private String email;

    /**
     * 提交说明
     */
    private String msg;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(long commitTime) {
        this.commitTime = commitTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
