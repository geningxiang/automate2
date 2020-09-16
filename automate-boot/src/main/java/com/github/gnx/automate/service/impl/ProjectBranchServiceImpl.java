package com.github.gnx.automate.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.gnx.automate.entity.ProjectBranchEntity;
import com.github.gnx.automate.event.bean.BranchUpdatedEvent;
import com.github.gnx.automate.repository.ProjectBranchRepository;
import com.github.gnx.automate.service.IProjectBranchService;
import com.github.gnx.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/28 21:57
 */
@Service
public class ProjectBranchServiceImpl implements IProjectBranchService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectBranchRepository projectBranchRepository;

    @Override
    public List<ProjectBranchEntity> getList(int projectId){
        return projectBranchRepository.getAllByProjectIdOrderByLastCommitTimeDescId(projectId);
    }

    /**
     * 项目分支 vcs更新时 触发
     * @param branchUpdatedEvent
     */
    @EventListener
    @Async
    public void onProjectBranchUpdated(BranchUpdatedEvent branchUpdatedEvent) {
        if (branchUpdatedEvent != null && branchUpdatedEvent.getProjectId() > 0 && StringUtils.isNotBlank(branchUpdatedEvent.getBranchName())) {
            logger.info("projectId:{}-{}分支更新", branchUpdatedEvent.getProjectId(), branchUpdatedEvent.getBranchName());

            ProjectBranchEntity model = projectBranchRepository.getFirstByProjectIdAndBranchName(branchUpdatedEvent.getProjectId(), branchUpdatedEvent.getBranchName());

            if (model == null) {
                model = new ProjectBranchEntity();
                model.setProjectId(branchUpdatedEvent.getProjectId());
                model.setBranchName(branchUpdatedEvent.getBranchName());
                model.setAutoType(ProjectBranchEntity.AutoType.NONE);
            }

            List<CommitLog> commitLogList = branchUpdatedEvent.getCommitLogList();

            model.setLastCommitId(commitLogList.get(0).getId());
            model.setLastCommitTime(new Timestamp(commitLogList.get(0).getCommitTime()));
            model.setLastCommitUser(commitLogList.get(0).getAuthor());
            model.setCommitLog(JSON.toJSONString(commitLogList));
            model.setUpdateTime(new Timestamp(System.currentTimeMillis()));

            projectBranchRepository.save(model);
        }
    }

}
