package com.github.gnx.automate.event.bean;

import com.github.gnx.automate.event.AbstractEvent;
import com.github.gnx.automate.vcs.vo.CommitLog;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 20:49
 */
public class BranchUpdatedEvent extends ApplicationEvent {

    private int projectId;
    private String branchName;
    private List<CommitLog> commitLogList;

    public BranchUpdatedEvent(Object source, int projectId, String branchName, List<CommitLog> commitLogList) {
        super(source);
        this.projectId = projectId;
        this.branchName = branchName;
        this.commitLogList = commitLogList;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getBranchName() {
        return branchName;
    }

    public List<CommitLog> getCommitLogList() {
        return commitLogList;
    }

}
