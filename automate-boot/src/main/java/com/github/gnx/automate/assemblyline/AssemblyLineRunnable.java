package com.github.gnx.automate.assemblyline;

import com.alibaba.fastjson.JSONArray;
import com.github.gnx.automate.assemblyline.config.AssemblyLineStepTask;
import com.github.gnx.automate.assemblyline.config.IAssemblyLineTaskConfig;
import com.github.gnx.automate.common.SpringUtils;
import com.github.gnx.automate.common.SystemUtil;
import com.github.gnx.automate.common.utils.TarUtils;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.AssemblyLineTaskLogEntity;
import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.exec.ExecEnvConfig;
import com.github.gnx.automate.exec.IExecConnection;
import com.github.gnx.automate.exec.IExecTemplate;
import com.github.gnx.automate.exec.docker.DockerSSHConnetction;
import com.github.gnx.automate.service.IAssemblyLineLogService;
import com.github.gnx.automate.service.IAssemblyLineTaskLogService;
import com.github.gnx.automate.service.IProjectService;
import com.github.gnx.automate.vcs.VcsHelper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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


    private IAssemblyLineTaskLogService assemblyLineTaskLogService;
    private IAssemblyLineLogService assemblyLineLogService;
    private IProjectService projectService;
    private VcsHelper vcsHelper;


    public AssemblyLineRunnable(int assemblyLineLogId, IAssemblyLineRunnableListener assemblyLineRunnableListener) {
        this.assemblyLineLogId = assemblyLineLogId;

        //TODO 监听器  暂时没有用起来
        this.assemblyLineRunnableListener = assemblyLineRunnableListener;

        this.assemblyLineTaskLogService = SpringUtils.getBean(IAssemblyLineTaskLogService.class);
        this.assemblyLineLogService = SpringUtils.getBean(IAssemblyLineLogService.class);
        this.projectService = SpringUtils.getBean(IProjectService.class);
        this.vcsHelper = SpringUtils.getBean(VcsHelper.class);
    }

    @Override
    public void run() {


        AssemblyLineLogEntity assemblyLineLogEntity = assemblyLineLogService.findById(this.assemblyLineLogId).get();
        this.assemblyLineRunnableListener.onStart(this);
        boolean success = false;
        try {
            Optional<ProjectEntity> projectEntity = projectService.findById(assemblyLineLogEntity.getProjectId());
            if (!projectEntity.isPresent()) {
                throw new RuntimeException("未找到相应的项目");
            }

            //解析流水线
            List<AssemblyLineStepTask> assemblyLineStepTasks = JSONArray.parseArray(assemblyLineLogEntity.getConfig(), AssemblyLineStepTask.class);


            //获取当前系统配置的 exec执行模板
            IExecTemplate execTemplate = ExecEnvConfig.getExecTemplate();

            try (IExecConnection execConnection = execTemplate.createConnection()) {
                final AssemblyLineEnv env = new AssemblyLineEnv(projectEntity.get(), assemblyLineLogEntity, execConnection);
                assemblyLineLogEntity.appendLine("切换版本库, branch: " + assemblyLineLogEntity.getBranch() + " , commitId: " + assemblyLineLogEntity.getCommitId());
                //切换分支
                String vcsId = vcsHelper.checkOut(projectEntity.get(), assemblyLineLogEntity.getBranch(), assemblyLineLogEntity.getCommitId());
                assemblyLineLogEntity.setCommitId(vcsId );
                //挡墙项目版本
                assemblyLineLogEntity.appendLine("版本库切换后ID: " + vcsId);
                //暂时只有2种  本地执行 或者 docker执行
                if (execConnection instanceof DockerSSHConnetction) {
                    assemblyLineLogEntity.appendLine(" == 当前为docker构建模式 == ");
                    //上传源码
                    File dir = SystemUtil.getProjectSourceCodeDir(projectEntity.get());
                    assemblyLineLogEntity.appendLine("源码打包中...");
                    File tarGzFile = TarUtils.tarAndGz(dir, dir, "tmp", true);
                    assemblyLineLogEntity.appendLine("开始上传源码");
                    execConnection.upload(tarGzFile, "/work", true, assemblyLineLogEntity);
                    env.setBaseDir("/work/");
                    assemblyLineLogEntity.appendLine("源码上传完毕,切换项目基础目录");
                    assemblyLineLogEntity.appendLine(" == 前期准备工作完成 == ");
                }

                success = runTask(assemblyLineLogEntity, assemblyLineStepTasks, env);
            }


        } catch (Exception e) {
            assemblyLineLogEntity.appendLine("== 执行任务发生异常 ==").appendLine(ExceptionUtils.getStackTrace(e));
        } finally {
            this.assemblyLineRunnableListener.onFinished(this);
        }

        assemblyLineLogEntity.setEndTime(new Timestamp(System.currentTimeMillis()));
        assemblyLineLogEntity.setStatus(success ? AssemblyLineLogEntity.Status.SUCCESS : AssemblyLineLogEntity.Status.ERROR);
        assemblyLineLogService.update(assemblyLineLogEntity);

    }

    private boolean runTask(AssemblyLineLogEntity assemblyLineLogEntity, List<AssemblyLineStepTask> assemblyLineStepTasks, AssemblyLineEnv assemblyLineEnv) {
        int stepIndex = 0;
        boolean success = true;
        for (AssemblyLineStepTask stepTask : assemblyLineStepTasks) {
            int taskIndex = 0;
            for (IAssemblyLineTaskConfig specificTask : stepTask.getTasks()) {
                if (success) {
                    success = runSpecificTask(assemblyLineLogEntity, stepIndex, taskIndex, assemblyLineEnv, specificTask);
                } else {
                    //前面任务发生错误  这里就不新建日志了
                    return false;
                }
                taskIndex++;
            }
            stepIndex++;
        }
        return success;
    }

    /**
     * 执行具体的单步任务
     * @param assemblyLineLogEntity
     * @param stepIndex
     * @param taskIndex
     * @param assemblyLineEnv
     * @param specificTask
     * @return
     */
    private boolean runSpecificTask(AssemblyLineLogEntity assemblyLineLogEntity, int stepIndex, int taskIndex, AssemblyLineEnv assemblyLineEnv, IAssemblyLineTaskConfig specificTask) {
        AssemblyLineTaskLogEntity model = new AssemblyLineTaskLogEntity();
        model.setProjectId(assemblyLineLogEntity.getProjectId());
        model.setAssemblyLineLogId(assemblyLineLogEntity.getId());
        model.setStepIndex(stepIndex);
        model.setTaskIndex(taskIndex);
        model.setStatus(AssemblyLineLogEntity.Status.INIT);
        model.setStartTime(new Timestamp(System.currentTimeMillis()));
        boolean success = false;
        try {
            success = AssemblyLinePluginManager.execute(assemblyLineEnv, specificTask, model::append);
        } catch (Exception e) {
            model.appendLine(ExceptionUtils.getStackTrace(e));
        }
        model.setStatus(success ? AssemblyLineLogEntity.Status.SUCCESS : AssemblyLineLogEntity.Status.ERROR);
        model.setEndTime(new Timestamp(System.currentTimeMillis()));
        assemblyLineTaskLogService.save(model);
        return success;
    }

    public int getAssemblyLineLogId() {
        return assemblyLineLogId;
    }
}
