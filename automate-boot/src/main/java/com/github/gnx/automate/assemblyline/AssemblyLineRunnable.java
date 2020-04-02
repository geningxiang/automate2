package com.github.gnx.automate.assemblyline;

import com.alibaba.fastjson.JSONArray;
import com.github.gnx.automate.assemblyline.field.AssemblyLineStepTask;
import com.github.gnx.automate.assemblyline.field.ITaskConfig;
import com.github.gnx.automate.common.SpringUtils;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.AssemblyLineTaskLogEntity;
import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.service.IAssemblyLineLogService;
import com.github.gnx.automate.service.IAssemblyLineTaskLogService;
import com.github.gnx.automate.service.IProjectService;
import com.github.gnx.automate.vcs.VcsHelper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 21:27
 */
public class AssemblyLineRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(AssemblyLineRunnable.class);

    private final IAssemblyLineRunnableListener assemblyLineRunnableListener;

    private final int assemblyLineLogId;


    public AssemblyLineRunnable(int assemblyLineLogId, IAssemblyLineRunnableListener assemblyLineRunnableListener) {
        this.assemblyLineLogId = assemblyLineLogId;

        //TODO 监听器  暂时没有用起来
        this.assemblyLineRunnableListener = assemblyLineRunnableListener;

    }

    @Override
    public void run() {
        IAssemblyLineLogService assemblyLineLogService = SpringUtils.getBean(IAssemblyLineLogService.class);
        IAssemblyLineTaskLogService assemblyLineTaskLogService = SpringUtils.getBean(IAssemblyLineTaskLogService.class);
        IProjectService projectService = SpringUtils.getBean(IProjectService.class);
        VcsHelper vcsHelper = SpringUtils.getBean(VcsHelper.class);
        AssemblyLineLogEntity assemblyLineLogEntity = assemblyLineLogService.findById(this.assemblyLineLogId).get();

        try {



            final AssemblyLineEnv assemblyLineEnv = new AssemblyLineEnv();


            Optional<ProjectEntity> projectEntity = projectService.getModel(assemblyLineLogEntity.getProjectId());
            projectEntity.ifPresent(assemblyLineEnv::setProjectEntity);

            //切换分支

            vcsHelper.checkOut(projectEntity.get(), assemblyLineLogEntity.getBranch(), assemblyLineLogEntity.getCommitId());


            List<AssemblyLineStepTask> assemblyLineStepTasks = JSONArray.parseArray(assemblyLineLogEntity.getConfig(), AssemblyLineStepTask.class);


            this.assemblyLineRunnableListener.onStart(this);
            //保存 日志


            int stepIndex = 0;
            boolean success = true;
            for (AssemblyLineStepTask stepTask : assemblyLineStepTasks) {
                int taskIndex = 0;
                for (ITaskConfig specificTask : stepTask.getTasks()) {
                    if(success) {
                        success = runSpecificTask(assemblyLineLogEntity, stepIndex, taskIndex, assemblyLineEnv, specificTask, assemblyLineTaskLogService);
                    } else {
                        //前面任务发生错误  这里就不新建日志了
                    }

                    taskIndex++;

                }
                stepIndex++;
            }

            assemblyLineLogEntity.setEndTime(new Timestamp(System.currentTimeMillis()));
            assemblyLineLogEntity.setStatus(success ? AssemblyLineLogEntity.Status.ERROR : AssemblyLineLogEntity.Status.SUCCESS);
            assemblyLineLogService.update(assemblyLineLogEntity);

        } catch (Exception e) {
            assemblyLineLogEntity.setEndTime(new Timestamp(System.currentTimeMillis()));
            assemblyLineLogEntity.setContent(ExceptionUtils.getStackTrace(e));
            assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.ERROR);
            assemblyLineLogService.update(assemblyLineLogEntity);
        } finally {
            this.assemblyLineRunnableListener.onFinished(this);
        }
    }

    private boolean runSpecificTask(AssemblyLineLogEntity assemblyLineLogEntity, int stepIndex, int taskIndex, AssemblyLineEnv assemblyLineEnv, ITaskConfig specificTask, IAssemblyLineTaskLogService assemblyLineTaskLogService) {
        AssemblyLineTaskLogEntity model = new AssemblyLineTaskLogEntity();
        model.setProjectId(assemblyLineLogEntity.getProjectId());
        model.setAssemblyLineLogId(assemblyLineLogEntity.getId());
        model.setStepIndex(stepIndex);
        model.setTaskIndex(taskIndex);
        model.setStatus(AssemblyLineLogEntity.Status.INIT);
        model.setStartTime(new Timestamp(System.currentTimeMillis()));
        boolean success = true;
        try {

            boolean result = AssemblyLinePluginManager.execute(assemblyLineEnv, specificTask, model::appendLine);
            model.setStatus(result ? AssemblyLineLogEntity.Status.SUCCESS : AssemblyLineLogEntity.Status.ERROR);
        } catch (Exception e) {
            model.appendLine(ExceptionUtils.getStackTrace(e));
            model.setStatus(AssemblyLineLogEntity.Status.ERROR);
            success = false;
        }

        model.setEndTime(new Timestamp(System.currentTimeMillis()));
        assemblyLineTaskLogService.save(model);
        return success;
    }

    public int getAssemblyLineLogId() {
        return assemblyLineLogId;
    }
}
