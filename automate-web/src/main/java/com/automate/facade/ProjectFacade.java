package com.automate.facade;

import com.alibaba.fastjson.JSON;
import com.automate.dao.ProjectBranchDAO;
import com.automate.entity.ProjectBranchEntity;
import com.automate.entity.ProjectEntity;
import com.automate.service.ProjectBranchService;
import com.automate.vcs.ICVSHelper;
import com.automate.vcs.git.GitHelper;
import com.automate.vcs.vo.CommitLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 0:05
 */
@Service
public class ProjectFacade {

    @Autowired
    private ProjectBranchService projectBranchService;

    /**
     * 初始化项目
     */
    public void init(ProjectEntity projectEntity) throws Exception {
        ICVSHelper cvsHelper = new GitHelper(projectEntity);
        List<String> branchList = cvsHelper.init();
        sync(projectEntity, cvsHelper, branchList);
    }

    /**
     * 同步项目
     */
    public void sync(ProjectEntity projectEntity) throws Exception {
        ICVSHelper cvsHelper = new GitHelper(projectEntity);

        List<String> updateBranchList = cvsHelper.update();
        sync(projectEntity, cvsHelper, updateBranchList);
    }

    private void sync(ProjectEntity projectEntity, ICVSHelper cvsHelper, List<String> branchList) throws Exception {
        for (String branchName : branchList) {
            List<CommitLog> commitLogs = cvsHelper.commitLogs(branchName);
            projectBranchService.update(projectEntity.getId(), branchName, commitLogs);
        }
    }
}
