package com.automate.task.background.update;

import com.automate.common.utils.SpringContextUtil;
import com.automate.entity.ApplicationUpdateApplyEntity;
import com.automate.service.ApplicationUpdateLogService;
import com.automate.task.background.AbstractBackgroundTask;
import com.automate.task.background.BackgroundLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/8 23:34
 */
public class BackgroundUpdateTask extends AbstractBackgroundTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ApplicationUpdateApplyEntity applicationUpdateApplyEntity;

    public BackgroundUpdateTask(ApplicationUpdateApplyEntity applicationUpdateApplyEntity) throws Exception {
        super(new BackgroundLock.Builder().addLockByApplication(applicationUpdateApplyEntity.getApplicationId()));
        this.applicationUpdateApplyEntity = applicationUpdateApplyEntity;

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void run() {
        ApplicationUpdateLogService applicationUpdateLogService = SpringContextUtil.getBean("applicationUpdateLogService", ApplicationUpdateLogService.class);


        try {
            applicationUpdateLogService.doUpdate(this.applicationUpdateApplyEntity);
        } catch (Exception e) {
            logger.error("应用更新任务发生错误", e);
        }
    }
}