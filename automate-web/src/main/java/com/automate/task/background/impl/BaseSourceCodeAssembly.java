package com.automate.task.background.impl;

import com.automate.common.SystemConfig;
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

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.*;

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
    private final AssemblyLineLogEntity assemblyLineLogEntity;

    private BaseSourceCodeAssembly(AssemblyLineEntity assemblyLineEntity, @NonNull String branchName, @Nullable String commitId, AssemblyLineLogEntity assemblyLineLogEntity,List<ITask> tasks, Set<String> locks) {
        super(locks);
        this.assemblyLineEntity = assemblyLineEntity;
        this.branchName = branchName;
        this.commitId = commitId;
        this.assemblyLineLogEntity = assemblyLineLogEntity;
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
        return new BaseSourceCodeAssembly(assemblyLineEntity, branchName, commitId, assemblyLineLogEntity, tasks, locks);
    }



    @Override
    public String getName() {
        return this.assemblyLineEntity.getName();
    }

    @Override
    public void run() {
        if (tasks != null) {
            SourceCodeService sourceCodeService = SpringContextUtil.getBean("sourceCodeService", SourceCodeService.class);
            AssemblyLineTaskLogService assemblyLineTaskLogService = SpringContextUtil.getBean("assemblyLineTaskLogService", AssemblyLineTaskLogService.class);
            AssemblyLineLogService assemblyLineLogService = SpringContextUtil.getBean("assemblyLineLogService", AssemblyLineLogService.class);


            int finished = 0;
            try {

                assemblyLineLogEntity.setStartTime(new Timestamp(System.currentTimeMillis()));
                assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.running);

                //
                Optional<SourceCodeEntity> sourceCodeEntity = sourceCodeService.getModel(this.assemblyLineEntity.getSourceCodeId());
                if(sourceCodeEntity.isPresent()){
                    //先将代码仓库切换到指定版本
                    GitHelper gitHelper = new GitHelper(sourceCodeEntity.get());
                    assemblyLineLogEntity.setCommitId(gitHelper.checkOut(this.branchName, this.commitId));

                    for (ITask task : tasks) {
                        task.invoke(sourceCodeEntity.get());

                        assemblyLineTaskLogService.save(task.getAssemblyLineTaskLogEntity());
                        finished++;
                    }
                }
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));

                for (int i = finished; i < tasks.size(); i++) {
                    AssemblyLineTaskLogEntity taskLog = tasks.get(i).getAssemblyLineTaskLogEntity();
                    if(i == finished){
                        taskLog.setContent(sw.toString());
                    }
                    taskLog.setStatus(-1);
                    if(taskLog.getStartTime() == null){
                        taskLog.setStartTime(new Timestamp(System.currentTimeMillis()));
                    }
                    taskLog.setEndTime(new Timestamp(System.currentTimeMillis()));
                    assemblyLineTaskLogService.save(taskLog);
                }
            } finally {
                //TODO 终止处理
                assemblyLineLogEntity.setEndTime(new Timestamp(System.currentTimeMillis()));
                if(finished == tasks.size()){
                    assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.success);
                } else {
                    assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.error);
                }
                assemblyLineLogService.save(assemblyLineLogEntity);
            }

        }
    }
}
