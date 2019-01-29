package com.automate.service;

import com.alibaba.fastjson.JSON;
import com.automate.dao.ProjectBranchDAO;
import com.automate.entity.ProjectBranchEntity;
import com.automate.entity.ProjectEntity;
import com.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/28 23:25
 */
@Service
public class ProjectBranchService {

    @Autowired
    private ProjectBranchDAO projectBranchDAO;

    @Autowired
    private EntityManager entityManager;

    public Iterable<ProjectBranchEntity> findAll() {
        return projectBranchDAO.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<ProjectBranchEntity> getModel(int id) {
        return projectBranchDAO.findById(id);
    }

    /**
     * 添加更新对象
     **/
    public void save(ProjectBranchEntity model) {
        projectBranchDAO.save(model);
    }

    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        projectBranchDAO.deleteById(id);
    }

    public void update(int projectId, String branchName, List<CommitLog> commitLogs){
        if (projectId <= 0 || StringUtils.isEmpty(branchName) || commitLogs.size() == 0) {
            return;
        }
        ProjectBranchEntity projectBranchEntity = projectBranchDAO.findFirstByProjectIdAndBranchNameOrderByIdDesc(projectId, branchName);

        if (projectBranchEntity == null) {
            projectBranchEntity = new ProjectBranchEntity();
            projectBranchEntity.setProjectId(projectId);
            projectBranchEntity.setBranchName(branchName);
        }
        CommitLog lastCommit = commitLogs.get(0);

        if (lastCommit.getId().equals(projectBranchEntity.getLastCommitId())) {
            //无变化
            return;
        }

        projectBranchEntity.setLastCommitId(lastCommit.getId());
        projectBranchEntity.setLastCommitTime(new Timestamp(lastCommit.getCommitTime()));
        projectBranchEntity.setLastCommitUser(lastCommit.getCommitter().getName());
        projectBranchEntity.setCommitLog(JSON.toJSONString(commitLogs));
        projectBranchEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        projectBranchDAO.save(projectBranchEntity);
    }
}
