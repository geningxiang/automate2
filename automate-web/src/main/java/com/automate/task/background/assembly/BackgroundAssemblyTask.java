package com.automate.task.background.assembly;

import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.AssemblyLineEntity;
import com.automate.entity.AssemblyLineLogEntity;
import com.automate.entity.SourceCodeEntity;
import com.automate.service.AssemblyLineLogService;
import com.automate.service.SourceCodeService;
import com.automate.task.background.AbstractBackgroundTask;
import com.automate.vcs.git.GitHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:19
 */
public class BackgroundAssemblyTask extends AbstractBackgroundTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final AssemblyLineEntity assemblyLineEntity;
    private final String branchName;
    private final String commitId;
    private final List<IAssemblyStepTask> tasks;
    private final AssemblyLineLogEntity assemblyLineLogEntity;


    private BackgroundAssemblyTask(AssemblyLineEntity assemblyLineEntity, @NonNull String branchName, @Nullable String commitId, AssemblyLineLogEntity assemblyLineLogEntity, List<IAssemblyStepTask> tasks, Set<String> locks) {
        super(locks);
        this.assemblyLineEntity = assemblyLineEntity;
        this.branchName = branchName;
        this.commitId = commitId;
        this.assemblyLineLogEntity = assemblyLineLogEntity;
        this.tasks = tasks;
    }

    public static BackgroundAssemblyTask create(AssemblyLineEntity assemblyLineEntity, @NonNull String branchName, @Nullable String commitId) throws Exception {
        Set<String> locks = new HashSet<>(16);
        locks.add("SOURCE_CODE_" + assemblyLineEntity.getSourceCodeId());
        List<IAssemblyStepTask> tasks = TaskConfigFormat.parse(assemblyLineEntity.getConfig());
        Map<String, Object> localCacheMap = new HashMap<>();

        AssemblyLineLogService assemblyLineLogService = SpringContextUtil.getBean("assemblyLineLogService", AssemblyLineLogService.class);
        AssemblyLineLogEntity assemblyLineLogEntity = new AssemblyLineLogEntity();
        assemblyLineLogEntity.setAssemblyLineId(assemblyLineEntity.getId());
        assemblyLineLogEntity.setSourceCodeId(assemblyLineEntity.getSourceCodeId());
        assemblyLineLogEntity.setBranch(branchName);
        assemblyLineLogEntity.setCommitId(commitId);
        assemblyLineLogEntity.setConfig(assemblyLineEntity.getConfig());
        assemblyLineLogEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.init);
        assemblyLineLogService.save(assemblyLineLogEntity);

        for (IAssemblyStepTask task : tasks) {

            if (task.getLocks() != null) {
                for (String lock : task.getLocks()) {
                    locks.add(lock);
                }
            }
            task.init(localCacheMap, assemblyLineEntity.getSourceCodeId(), branchName, commitId, 0, 0, assemblyLineLogEntity);
        }
        return new BackgroundAssemblyTask(assemblyLineEntity, branchName, commitId, assemblyLineLogEntity, tasks, locks);
    }


    @Override
    public String getName() {
        return this.assemblyLineEntity.getName();
    }

    @Override
    public void run() {
        if (tasks != null) {
            SourceCodeService sourceCodeService = SpringContextUtil.getBean("sourceCodeService", SourceCodeService.class);
            AssemblyLineLogService assemblyLineLogService = SpringContextUtil.getBean("assemblyLineLogService", AssemblyLineLogService.class);


            boolean hasError = false;
            try {

                assemblyLineLogEntity.setStartTime(new Timestamp(System.currentTimeMillis()));
                assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.running);

                //
                Optional<SourceCodeEntity> sourceCodeEntity = sourceCodeService.getModel(this.assemblyLineEntity.getSourceCodeId());
                if (sourceCodeEntity.isPresent()) {
                    //先将代码仓库切换到指定版本
                    GitHelper gitHelper = new GitHelper(sourceCodeEntity.get());
                    assemblyLineLogEntity.setCommitId(gitHelper.checkOut(this.branchName, this.commitId));

                    for (IAssemblyStepTask task : tasks) {
                        if (hasError) {
                            task.cancel("前置步骤发生错误");
                        } else if (!task.invoke()) {
                            hasError = true;
                        }
                    }

                }
            } catch (Exception e) {
                logger.warn("流水线发生错误", e);
                hasError = true;
            } finally {
                //TODO 终止处理
                assemblyLineLogEntity.setEndTime(new Timestamp(System.currentTimeMillis()));
                if (hasError) {
                    assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.error);
                } else {
                    assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.success);
                }
                assemblyLineLogService.save(assemblyLineLogEntity);
            }

        }
    }
}
