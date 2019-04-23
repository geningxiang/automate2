package com.automate.event.po;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *  收到git 的 webhook push 事件
 * @author: genx
 * @date: 2019/2/21 22:01
 */
public class GitWebHookPushEvent implements IEvent {

    private final Integer sourceCodeId;
    private final String branchName;

    public GitWebHookPushEvent(Integer sourceCodeId, String branchName) {
        this.sourceCodeId = sourceCodeId;
        this.branchName = branchName;
    }

    public Integer getSourceCodeId() {
        return sourceCodeId;
    }

    public String getBranchName() {
        return branchName;
    }
}

