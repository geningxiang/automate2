package com.automate.vcs.git;

import com.alibaba.fastjson.JSONObject;
import com.automate.repository.HookLogRepository;
import com.automate.repository.ProjectRepository;
import com.automate.entity.ProjectEntity;
import com.automate.service.ProjectBranchService;
import com.automate.vcs.ICVSHelper;
import com.automate.vcs.vo.CommitLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 21:29
 */
public abstract class AbstractGitHook {

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected HookLogRepository hookLogRepository;

    @Autowired
    private ProjectBranchService projectBranchService;

    public abstract boolean match(HttpServletRequest request, JSONObject data);

    public abstract void handle(HttpServletRequest request, JSONObject data);

    protected JSONObject bulidHeaderJson(HttpServletRequest request) {
        JSONObject headerJson = new JSONObject();
        if (request != null) {
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String h = headerNames.nextElement();
                headerJson.put(h, request.getHeader(h));
            }
        }
        return headerJson;
    }

    /**
     * 暂时只需要考虑 单个分支的变化
     *
     * @param gitUrl
     * @param branchName
     */
    protected int branchChange(String gitUrl, String branchName) throws Exception {
        Assert.hasText(gitUrl, "gitUrl is empty");
        List<ProjectEntity> projectEntitys = projectRepository.getAllByVersionUrlOrderByIdDesc(gitUrl);

        if (projectEntitys.size() == 0) {
            throw new IllegalArgumentException("未找到相应的项目");
        } else if (projectEntitys.size() > 1) {
            throw new IllegalArgumentException("找到了多个项目,请删除重复:" + gitUrl);
        }

        ICVSHelper cvsHelper = new GitHelper(projectEntitys.get(0));
        int projectId = projectEntitys.get(0).getId();
        List<String> updatedBranchList = cvsHelper.update(branchName);

        for (String updatedBranchName : updatedBranchList) {
            List<CommitLog> commitLogs = cvsHelper.commitLogs(branchName);
            projectBranchService.update(projectId, updatedBranchName, commitLogs);
        }

        return projectId;
    }

}
