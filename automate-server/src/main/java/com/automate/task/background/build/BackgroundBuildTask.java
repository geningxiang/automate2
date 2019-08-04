package com.automate.task.background.build;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.AssemblyLineEntity;
import com.automate.entity.AssemblyLineLogEntity;
import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.service.AssemblyLineLogService;
import com.automate.service.AssemblyLineTaskLogService;
import com.automate.task.background.AbstractBackgroundTask;
import com.automate.task.background.BackgroundLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/4 16:23
 */
public class BackgroundBuildTask extends AbstractBackgroundTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private AssemblyLineEntity assemblyLineEntity;

    private AssemblyLineLogEntity assemblyLineLogEntity = new AssemblyLineLogEntity();
    private List<AssemblyLineTaskLogEntity> taskLogList = new ArrayList(32);
    private List<IBuildHandler> buildHandlerList = new ArrayList<>(32);


    public BackgroundBuildTask(AssemblyLineEntity assemblyLineEntity, @NonNull String branchName, @Nullable String commitId) throws Exception {
        super(new BackgroundLock.Builder().addLockByProject(assemblyLineEntity.getProjectId()));
        this.assemblyLineEntity = assemblyLineEntity;

        AssemblyLineLogService assemblyLineLogService = SpringContextUtil.getBean("assemblyLineLogService", AssemblyLineLogService.class);
        AssemblyLineTaskLogService assemblyLineTaskLogService = SpringContextUtil.getBean("assemblyLineTaskLogService", AssemblyLineTaskLogService.class);


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
                        "handler": "shell",
                        "content": "mvn package"
                    }
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

    }


}
