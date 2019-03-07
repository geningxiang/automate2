package com.automate.task.background.assembly;

import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.AssemblyLineLogEntity;
import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.entity.SourceCodeEntity;
import com.automate.service.AssemblyLineTaskLogService;
import com.automate.service.SourceCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/5 20:58
 */
public abstract class AbstractAssemblyStepTask implements IAssemblyStepTask {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private AssemblyLineTaskLogService assemblyLineTaskLogService;
    private AssemblyLineTaskLogEntity taskLog = null;

    private String name;

    /**
     * cache  同一个流水线共享
     */
    private Map<String, Object> localCacheMap = null;

    @Override
    public void init(Map<String, Object> localCacheMap, Integer sourceCodeId, String branch, String commitId, Integer serverId, Integer applicationId, Integer assemblyLineLogId) {
        this.localCacheMap = localCacheMap;
        assemblyLineTaskLogService = SpringContextUtil.getBean("assemblyLineTaskLogService", AssemblyLineTaskLogService.class);
        taskLog = new AssemblyLineTaskLogEntity();
        taskLog.setSourceCodeId(sourceCodeId);
        taskLog.setBranch(branch);
        taskLog.setCommitId(commitId);
        taskLog.setServerId(serverId);
        taskLog.setApplicationId(applicationId);
        taskLog.setAssemblyLineLogId(assemblyLineLogId);
        taskLog.setStatus(AssemblyLineLogEntity.Status.init);
//        assemblyLineTaskLogService.save(taskLog);
    }

    /**
     * 具体的执行方法
     *
     * @return
     */
    public abstract boolean doInvoke() throws Exception;

    @Override
    public boolean invoke() {
        if (taskLog == null) {
            logger.warn("taskLog is null");
            return false;
        }
        try {
            taskLog.setStartTime(new Timestamp(System.currentTimeMillis()));
            taskLog.setStatus(AssemblyLineLogEntity.Status.running);

            if (doInvoke()) {
                taskLog.setStatus(AssemblyLineLogEntity.Status.success);
                return true;
            } else {
                taskLog.setStatus(AssemblyLineLogEntity.Status.error);
                return false;
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));

            taskLog.appendLine(sw.toString());
            taskLog.setStatus(AssemblyLineLogEntity.Status.error);
            return false;
        }finally {
            taskLog.setEndTime(new Timestamp(System.currentTimeMillis()));
            assemblyLineTaskLogService.save(taskLog);
        }
    }

    @Override
    public void cancel(String reason ){
        taskLog.appendLine(reason);
        taskLog.setStatus(AssemblyLineLogEntity.Status.cancel);
        taskLog.setEndTime(new Timestamp(System.currentTimeMillis()));
        assemblyLineTaskLogService.save(taskLog);
    }

    protected void appendLine(String content) {
        if (this.taskLog != null) {
            this.taskLog.appendLine(content);
        }
    }

    protected SourceCodeEntity getSourceCodeEntity() {
        Assert.notNull(this.taskLog, "taskLog is required");


        if (localCacheMap != null) {
            SourceCodeEntity sourceCodeEntity = (SourceCodeEntity) localCacheMap.get("sourceCodeEntity");
            if (sourceCodeEntity != null) {
                logger.debug("在缓存中找到了sourceCodeEntity");
                return sourceCodeEntity;
            }

        }
        logger.debug("从数据库读取sourceCodeEntity");
        SourceCodeService sourceCodeService = SpringContextUtil.getBean("sourceCodeService", SourceCodeService.class);
        Optional<SourceCodeEntity> model = sourceCodeService.getModel(this.taskLog.getSourceCodeId());
        if (model.isPresent()) {
            localCacheMap.put("sourceCodeEntity", model.get());
            return model.get();
        }

        throw new IllegalArgumentException("sourceCodeEntity is null");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
