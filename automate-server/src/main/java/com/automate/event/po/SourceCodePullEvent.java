package com.automate.event.po;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/13 22:38
 */
public class SourceCodePullEvent implements IEvent {

    private final Integer projectId;
    private final String branchName;
    private final String commitId;

    public SourceCodePullEvent(Integer projectId, String branchName, String commitId) {
        this.projectId = projectId;
        this.branchName = branchName;
        this.commitId = commitId;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getCommitId() {
        return commitId;
    }
}
