package com.automate.vcs.git;

import com.alibaba.fastjson.JSONObject;
import com.automate.entity.SourceCodeEntity;
import com.automate.repository.HookLogRepository;
import com.automate.service.SourceCodeService;
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
    protected SourceCodeService sourceCodeService;

    @Autowired
    protected HookLogRepository hookLogRepository;

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
        SourceCodeEntity sourceCodeEntity = sourceCodeService.getFirstByVcsUrl(gitUrl);

        if (sourceCodeEntity == null) {
            throw new IllegalArgumentException("未找到相应的项目");
        }

        ICVSHelper cvsHelper = new GitHelper(sourceCodeEntity);

        List<String> updatedBranchList = cvsHelper.update(branchName);

        for (String updatedBranchName : updatedBranchList) {
            List<CommitLog> commitLogs = cvsHelper.commitLogs(branchName);
            sourceCodeService.updateBranch(sourceCodeEntity.getId(), updatedBranchName, commitLogs);
        }

        return sourceCodeEntity.getId();
    }

}
