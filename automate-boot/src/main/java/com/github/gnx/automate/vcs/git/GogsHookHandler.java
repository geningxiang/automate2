package com.github.gnx.automate.vcs.git;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.gnx.automate.entity.HookLogEntity;
import com.github.gnx.automate.vcs.IVcsHookHandler;
import com.github.gnx.automate.vcs.git.GitContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 说明文档见 https://github.com/gogs/docs-api/blob/master/Repositories/Webhooks.md
 *
 * @author: genx
 * @date: 2019/1/22 23:09
 */
@Component
public class GogsHookHandler implements IVcsHookHandler {

    private final String USER_AGENT = "user-agent";
    private final String GOGS_SERVER = "GogsServer";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean match(Map<String, String> headers, JSONObject data) {

        return GOGS_SERVER.equals(headers.get(USER_AGENT));
    }

    @Override
    public void doHandle(Map<String, String> headers, JSONObject data) {

        HookLogEntity hookLogEntity = new HookLogEntity();

        hookLogEntity.setSource(GOGS_SERVER);
        hookLogEntity.setDelivery(headers.get("x-gogs-delivery"));
        hookLogEntity.setRequestHeader(JSON.toJSONString(headers));
        if (data != null) {
            hookLogEntity.setRequestBody(data.toJSONString());
        }
        hookLogEntity.setResponse("200 ok");

        /*
        create  创建分支  单纯的create 并没有什么用  所以只需要判断 push 就好
        push    推送
        delete  删除分支
         */
        String event = headers.get("x-gogs-event");
        hookLogEntity.setEvent(event);


        if ("push".equals(event)) {
            String versionUrl = data.getJSONObject("repository").getString("clone_url");

            String ref = data.getString("ref");
            String branchName = ref.substring(GitContants.BRANCH_NAME_PREFIX_LEN);

            try {
//                int projectId = branchChange(versionUrl, branchName);
//                if (projectId > 0) {
//                    hookLogEntity.setProjectId(projectId);
//                    hookLogEntity.setHandleStatus(HookLogEntity.HandleStatus.PROCESSED);
//                    hookLogEntity.setHandleResult("已处理");
//                } else {
//                    hookLogEntity.setHandleStatus(HookLogEntity.HandleStatus.IGNORE);
//                    hookLogEntity.setHandleResult("未找到对应的项目");
//                }

            } catch (Exception e) {
                logger.error("hook处理时发生错误", e);
                hookLogEntity.setHandleStatus(HookLogEntity.HandleStatus.ERROR);
                hookLogEntity.setHandleResult(e.getMessage());
            }


        } else {
            hookLogEntity.setHandleStatus(HookLogEntity.HandleStatus.IGNORE);
            hookLogEntity.setHandleResult("不需要关注的事件");
        }
//        hookLogService.save(hookLogEntity);
    }
}
