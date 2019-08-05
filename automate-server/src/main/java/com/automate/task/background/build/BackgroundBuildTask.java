package com.automate.task.background.build;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.automate.common.SystemConfig;
import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.AssemblyLineEntity;
import com.automate.entity.AssemblyLineLogEntity;
import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.entity.ProjectEntity;
import com.automate.service.AssemblyLineLogService;
import com.automate.service.AssemblyLineTaskLogService;
import com.automate.service.ProjectService;
import com.automate.task.background.AbstractBackgroundTask;
import com.automate.task.background.BackgroundLock;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/4 16:23
 */
public class BackgroundBuildTask extends AbstractBackgroundTask {
    private Logger logger = LoggerFactory.getLogger(getClass());


    private AssemblyLineLogService assemblyLineLogService;
    private AssemblyLineTaskLogService assemblyLineTaskLogService;

    private int projectId;
    private AssemblyLineEntity assemblyLineEntity;
    private AssemblyLineLogEntity assemblyLineLogEntity = new AssemblyLineLogEntity();
    private List<AssemblyLineTaskLogEntity> taskLogList = new ArrayList(32);
    private List<IBuildHandler> buildHandlerList = new ArrayList<>(32);


    public BackgroundBuildTask(AssemblyLineEntity assemblyLineEntity, int projectId, @NonNull String branchName, @Nullable String commitId) throws Exception {
        super(new BackgroundLock.Builder().addLockByProject(assemblyLineEntity.getProjectId()));
        this.assemblyLineEntity = assemblyLineEntity;

        this.projectId = projectId;

        assemblyLineLogService = SpringContextUtil.getBean("assemblyLineLogService", AssemblyLineLogService.class);
        assemblyLineTaskLogService = SpringContextUtil.getBean("assemblyLineTaskLogService", AssemblyLineTaskLogService.class);


        assemblyLineLogEntity.setAssemblyLineId(assemblyLineEntity.getId());
        assemblyLineLogEntity.setProjectId(assemblyLineEntity.getProjectId());
        assemblyLineLogEntity.setBranch(branchName);
        assemblyLineLogEntity.setCommitId(commitId);
        assemblyLineLogEntity.setConfig(assemblyLineEntity.getConfig());
        assemblyLineLogEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.init);
        assemblyLineLogService.save(assemblyLineLogEntity);


        /*
        [
            {
                "lifeName": "package",
                "steps": [
                    {
                        "stepName": "mvn打包",
                        "className": "com.automate.task.background.build.impl.ShellHandler",
                        "scripts": "mvn package"
                    },
                    {
                        "stepName": "ping测试",
                        "className": "com.automate.task.background.build.impl.ShellHandler",
                        "scripts": "ping www.baidu.com"
                    },
                ]
            }
        ]
         */
        JSONArray lifeCycleTasks = JSONArray.parseArray(assemblyLineEntity.getConfig());

        JSONObject lifeCycleTask;
        JSONArray stepArray;
        JSONObject stepTask;
        for (int i = 0; i < lifeCycleTasks.size(); i++) {
            lifeCycleTask = lifeCycleTasks.getJSONObject(i);

            stepArray = lifeCycleTask.getJSONArray("steps");

            for (int j = 0; j < stepArray.size(); j++) {
                stepTask = stepArray.getJSONObject(j);

                AssemblyLineTaskLogEntity assemblyLineTaskLogEntity = new AssemblyLineTaskLogEntity();
                assemblyLineTaskLogEntity.setProjectId(assemblyLineEntity.getProjectId());
                assemblyLineTaskLogEntity.setAssemblyLineLogId(assemblyLineLogEntity.getId());
                assemblyLineTaskLogEntity.setLifeCycle(i);
                assemblyLineTaskLogEntity.setTaskIndex(j);
                assemblyLineTaskLogEntity.setStatus(AssemblyLineLogEntity.Status.init);
                assemblyLineTaskLogEntity.setStartTime(new Timestamp(System.currentTimeMillis()));
                taskLogList.add(assemblyLineTaskLogEntity);

                IBuildHandler buildHandler = BuildHandlerFormatter.parse(stepTask);

                buildHandlerList.add(buildHandler);
            }
        }

        assemblyLineTaskLogService.saveAll(taskLogList);

    }

    @Override
    public String getName() {
        return "构建任务-" + assemblyLineEntity.getId();
    }

    @Override
    public void run() {

        assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.running);
        assemblyLineLogEntity.setStartTime(new Timestamp(System.currentTimeMillis()));
        assemblyLineLogService.save(assemblyLineLogEntity);

        AssemblyLineTaskLogEntity item;
        IBuildHandler buildHandler;
        Map<String, Object> tempMap = new HashMap(64);

        ProjectService projectService = SpringContextUtil.getBean("projectService", ProjectService.class);


        Optional<ProjectEntity> projectEntity = projectService.getModel(projectId);

        //项目基础文件夹
        tempMap.put("baseDir", SystemConfig.getProjectBaseDir(projectEntity.get()));


        tempMap.put("projectId", this.projectId);
        tempMap.put("version", "");
        tempMap.put("branch", this.assemblyLineLogEntity.getBranch());
        tempMap.put("commitId", this.assemblyLineLogEntity.getCommitId());


        boolean result = true;
        for (int i = 0; i < taskLogList.size(); i++) {
            item = taskLogList.get(i);

            if (!result) {
                item.setStatus(AssemblyLineLogEntity.Status.cancel);
                assemblyLineTaskLogService.save(item);
                continue;
            }
            StringBuffer content = new StringBuffer(102400);
            try {
                buildHandler = buildHandlerList.get(i);
                buildHandler.verify();
                item.setStatus(AssemblyLineLogEntity.Status.running);
                assemblyLineTaskLogService.save(item);

                result = buildHandler.execute(tempMap, content);

            } catch (Exception e) {
                result = false;
                content.append(ExceptionUtils.getStackTrace(e));
            }

            item.setStatus(result ? AssemblyLineLogEntity.Status.success : AssemblyLineLogEntity.Status.error);
            item.setContent(content.toString());
            item.setEndTime(new Timestamp(System.currentTimeMillis()));
            assemblyLineTaskLogService.save(item);
        }


        assemblyLineLogEntity.setStatus(result ? AssemblyLineLogEntity.Status.success : AssemblyLineLogEntity.Status.error);
        assemblyLineLogEntity.setEndTime(new Timestamp(System.currentTimeMillis()));
        assemblyLineLogService.save(assemblyLineLogEntity);
    }


}
