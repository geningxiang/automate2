package com.automate.vcs.git;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 21:32
 */
@Component
public class GitHookHandler {

    @Autowired
    private AbstractGitHook[] gitHooks;


    public boolean handle(HttpServletRequest request, JSONObject data) {
        boolean isHandle = false;
        if(gitHooks != null && gitHooks.length > 0) {
            for (AbstractGitHook gitHook : gitHooks) {
                if(gitHook.match(request, data)){
                    gitHook.handle(request, data);
                    isHandle = true;
                    break;
                }
            }
        }
        return isHandle;
    }
}
