package com.automate.service;

import com.alibaba.fastjson.JSON;
import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.ProjectEntity;
import com.automate.entity.ProjectBranchEntity;
import com.automate.repository.ProjectRepository;
import com.automate.repository.ProjectBranchRepository;
import com.automate.vcs.IVCSHelper;
import com.automate.vcs.VCSHelper;
import com.automate.vcs.git.GitHelper;
import com.automate.vcs.vo.CommitLog;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:26
 */
@Service
public class ProjectService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * initialCapacity 设置cache的初始大小，要合理设置该值
     */
    private static Cache<Integer, ProjectEntity> PROJECT_CACHE_MAP = Caffeine.newBuilder()
            .initialCapacity(256)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectBranchRepository projectBranchRepository;

    /**
     * 初始化本地代码库
     */
    @Deprecated
    public void init(ProjectEntity sourceCodeEntity) throws Exception {
        IVCSHelper cvsHelper = new GitHelper(sourceCodeEntity);
        Set<String> branchList = cvsHelper.init();
        sync(sourceCodeEntity, cvsHelper, branchList);
    }

    /**
     * 同步代码库
     * TODO 需要后台执行
     */
    public int sync(@NonNull ProjectEntity sourceCodeEntity) throws Exception {
        logger.info("开始同步代码仓库:{}", sourceCodeEntity.toJson());

        IVCSHelper cvsHelper = VCSHelper.create(sourceCodeEntity);
        //检查一下
        Set<String> updateBranchList;
        int total = 0;
        if (!cvsHelper.isLocalRepositoryExist()) {
            updateBranchList = cvsHelper.init();
            total += sync(sourceCodeEntity, cvsHelper, updateBranchList);
        }
        return total;
    }

    public int sync(ProjectEntity sourceCodeEntity, IVCSHelper cvsHelper, Collection<String> branchList) throws Exception {
        for (String branchName : branchList) {
            List<CommitLog> commitLogs = cvsHelper.commitLogs(branchName);
            this.updateBranch(sourceCodeEntity.getId(), branchName, commitLogs);
        }
        return branchList.size();
    }

    public void updateBranch(int sourceCodeId, String branchName, List<CommitLog> commitLogs) {
        if (sourceCodeId <= 0 || StringUtils.isEmpty(branchName) || commitLogs.size() == 0) {
            return;
        }
        ProjectBranchEntity projectBranchEntity = projectBranchRepository.findFirstByProjectIdAndBranchName(sourceCodeId, branchName);

        if (projectBranchEntity == null) {
            projectBranchEntity = new ProjectBranchEntity();
            projectBranchEntity.setProjectId(sourceCodeId);
            projectBranchEntity.setBranchName(branchName);
            projectBranchEntity.setAutoType(ProjectBranchEntity.AutoType.NONE);
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
        projectBranchRepository.save(projectBranchEntity);
    }

    public ProjectEntity getFirstByVcsUrl(String vcsUrl) {
        return projectRepository.getFirstByVcsUrl(vcsUrl);
    }

    public Iterable<ProjectEntity> findAll() {
        return projectRepository.findAll(Sort.by("id"));
    }

    public Map<Integer, ProjectEntity> findAllWidthMap() {
        Iterable<ProjectEntity> list = this.findAll();
        Map<Integer, ProjectEntity> map = new HashMap<>(64);
        for (ProjectEntity projectEntity : list) {
            map.put(projectEntity.getId(), projectEntity);
        }
        return map;
    }

    /**
     * 查询对象
     **/
    public Optional<ProjectEntity> getModel(int id) {
        return projectRepository.findById(id);
    }

    private static ProjectEntity getModelStatic(int id) {
        Optional<ProjectEntity> model = SpringContextUtil.getBean("projectService", ProjectService.class).getModel(id);
        return model.isPresent() ? model.get() : null;
    }

    public static ProjectEntity getModelByCache(int id) {
        return PROJECT_CACHE_MAP.get(id, ProjectService::getModelStatic);
    }

    /**
     * 添加对象
     **/
    public void save(ProjectEntity model) {
        if (model.getCreateTime() == null) {
            model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        projectRepository.save(model);

        PROJECT_CACHE_MAP.put(model.getId(), model);
    }

}
