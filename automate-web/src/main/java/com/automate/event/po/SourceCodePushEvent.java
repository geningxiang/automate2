package com.automate.event.po;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/13 22:38
 */
public class SourceCodePushEvent implements IEvent {

    private final Integer sourceCodeId;
    private final String branchName;
    private final String commitId;

    public SourceCodePushEvent(Integer sourceCodeId, String branchName, String commitId) {
        this.sourceCodeId = sourceCodeId;
        this.branchName = branchName;
        this.commitId = commitId;
    }

    public int getSourceCodeId() {
        return sourceCodeId;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getCommitId() {
        return commitId;
    }
}
