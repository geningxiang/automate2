package com.automate.task.background.impl;

import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.AssemblyLineEntity;
import com.automate.entity.AssemblyLineLogEntity;
import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.entity.SourceCodeEntity;
import com.automate.service.AssemblyLineLogService;
import com.automate.service.AssemblyLineTaskLogService;
import com.automate.service.SourceCodeService;
import com.automate.task.background.AbstractBackgroundAssembly;
import com.automate.task.background.ITask;
import com.automate.task.background.TaskConfigFormat;
import com.automate.vcs.git.GitHelper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 16:19
 */
public class BaseSourceCodeAssembly extends AbstractBackgroundAssembly {

    private final AssemblyLineEntity assemblyLineEntity;
    private final String branchName;
    private final String commitId;
    private final List<ITask> tasks;

    private BaseSourceCodeAssembly(AssemblyLineEntity assemblyLineEntity, @NonNull String branchName, @Nullable String commitId, List<ITask> tasks, Set<String> locks) {
        super(locks);
        this.assemblyLineEntity = assemblyLineEntity;
        this.branchName = branchName;
        this.commitId = commitId;
        this.tasks = tasks;
    }

    public static BaseSourceCodeAssembly create(AssemblyLineEntity assemblyLineEntity, @NonNull String branchName, @Nullable String commitId) throws Exception {
        Set<String> locks = new HashSet<>(16);
        locks.add("SOURCE_CODE_" + assemblyLineEntity.getSourceCodeId());
        List<ITask> tasks = TaskConfigFormat.parse(assemblyLineEntity.getConfig());


        AssemblyLineLogService assemblyLineLogService = SpringContextUtil.getBean("assemblyLineLogService", AssemblyLineLogService.class);
        AssemblyLineTaskLogService assemblyLineTaskLogService = SpringContextUtil.getBean("assemblyLineTaskLogService", AssemblyLineTaskLogService.class);

        AssemblyLineLogEntity assemblyLineLogEntity = new AssemblyLineLogEntity();
        assemblyLineLogEntity.setAssemblyLineId(assemblyLineEntity.getId());
        assemblyLineLogEntity.setSourceCodeId(assemblyLineEntity.getSourceCodeId());
        assemblyLineLogEntity.setBranch(branchName);
        assemblyLineLogEntity.setCommitId(commitId);
        assemblyLineLogEntity.setConfig(assemblyLineEntity.getConfig());
        assemblyLineLogEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.init);

        assemblyLineLogService.save(assemblyLineLogEntity);

        for (ITask task : tasks) {
            task.setSourceCodeId(assemblyLineEntity.getSourceCodeId());
            if (task.getLocks() != null) {
                for (String lock : task.getLocks()) {
                    locks.add(lock);
                }
            }

            AssemblyLineTaskLogEntity model = new AssemblyLineTaskLogEntity();
            model.setSourceCodeId(assemblyLineEntity.getSourceCodeId());
            model.setBranch(branchName);
            model.setCommitId(commitId);
            model.setServerId(0);
            model.setApplicationId(0);
            model.setAssemblyLineLogId(assemblyLineLogEntity.getId());
            assemblyLineTaskLogService.save(model);
            task.setAssemblyLineTaskLogEntity(model);

        }
        return new BaseSourceCodeAssembly(assemblyLineEntity, branchName, commitId, tasks, locks);
    }



    @Override
    public String getName() {
        return this.assemblyLineEntity.getName();
    }

    @Override
    public void run() {
        if (tasks != null) {


            try {
                SourceCodeService sourceCodeService = SpringContextUtil.getBean("sourceCodeService", SourceCodeService.class);
                AssemblyLineTaskLogService assemblyLineTaskLogService = SpringContextUtil.getBean("assemblyLineTaskLogService", AssemblyLineTaskLogService.class);


                //
                Optional<SourceCodeEntity> sourceCodeEntity = sourceCodeService.getModel(this.assemblyLineEntity.getSourceCodeId());
                if(sourceCodeEntity.isPresent()){
                    //先将代码仓库切换到指定版本
                    GitHelper gitHelper = new GitHelper(sourceCodeEntity.get());
                    gitHelper.checkOut(this.branchName, this.commitId);

                    for (ITask task : tasks) {
                        task.invoke(sourceCodeEntity.get());

                        assemblyLineTaskLogService.save(task.getAssemblyLineTaskLogEntity());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //TODO 终止处理
            }

        }
    }
}
