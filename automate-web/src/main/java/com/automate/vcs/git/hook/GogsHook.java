package com.automate.vcs.git.hook;

import com.alibaba.fastjson.JSONObject;
import com.automate.entity.HookLogEntity;
import com.automate.entity.ProjectEntity;
import com.automate.vcs.git.AbstractGitHook;
import com.automate.vcs.git.GitContants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 说明文档见 https://github.com/gogs/docs-api/blob/master/Repositories/Webhooks.md
 *
 * @author: genx
 * @date: 2019/1/22 23:09
 */
@Component
public class GogsHook extends AbstractGitHook {

    private final String GOGS_SERVER = "GogsServer";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean match(HttpServletRequest request, JSONObject data) {
        return GOGS_SERVER.equals(request.getHeader("user-agent"));
    }

    @Override
    public void handle(HttpServletRequest request, JSONObject data) {

        HookLogEntity hookLogEntity = new HookLogEntity();

        hookLogEntity.setSource(GOGS_SERVER);
        hookLogEntity.setDelivery(request.getHeader("x-gogs-delivery"));
        hookLogEntity.setRequestHeader(bulidHeaderJson(request).toJSONString());
        if(data != null){
            hookLogEntity.setRequestBody(data.toJSONString());
        }
        hookLogEntity.setResponse("200 ok");

        /*
        create  创建分支  单纯的create 并没有什么用  所以只需要判断 push 就好
        push    推送
        delete  删除分支
         */
        String event = request.getHeader("x-gogs-event");
        hookLogEntity.setEvent(event);


        if("push".equals(event)){
            String versionUrl = data.getJSONObject("repository").getString("clone_url");

            String ref = data.getString("ref");
            String branchName = ref.substring(GitContants.BRANCH_NAME_PREFIX_LEN);

            try {
                int projectId = branchChange(versionUrl, branchName);
                if(projectId > 0){
                    hookLogEntity.setProjectId(projectId);
                    hookLogEntity.setHandleStatus(HookLogEntity.HandleStatus.PROCESSED);
                    hookLogEntity.setHandleResult("已处理");
                } else {
                    hookLogEntity.setHandleStatus(HookLogEntity.HandleStatus.IGNORE);
                    hookLogEntity.setHandleResult("未找到对应的项目");
                }

            } catch (Exception e) {
                logger.error("hook处理时发生错误", e);
                hookLogEntity.setHandleStatus(HookLogEntity.HandleStatus.ERROR);
                hookLogEntity.setHandleResult(e.getMessage());
            }


        } else {
            hookLogEntity.setHandleStatus(HookLogEntity.HandleStatus.IGNORE);
            hookLogEntity.setHandleResult("不需要关注的事件");
        }
        hookLogDAO.save(hookLogEntity);
    }
}
