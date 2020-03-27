package com.github.gnx.automate.vcs.vo;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 12:41
 */
public class VcsBranch {


    /**
     * 分支名称
     */
    private String branchName;

    /**
     * 最后提交sha
     */
    private String lastCommitId;

    /**
     * 最后提交时间戳
     */
    private Long lastCommitTime;

    /**
     * 最后提交者
     */
    private String lastCommitter;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLastCommitId() {
        return lastCommitId;
    }

    public void setLastCommitId(String lastCommitId) {
        this.lastCommitId = lastCommitId;
    }

    public Long getLastCommitTime() {
        return lastCommitTime;
    }

    public void setLastCommitTime(Long lastCommitTime) {
        this.lastCommitTime = lastCommitTime;
    }

    public String getLastCommitter() {
        return lastCommitter;
    }

    public void setLastCommitter(String lastCommitter) {
        this.lastCommitter = lastCommitter;
    }
}
