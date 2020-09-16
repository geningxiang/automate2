package com.automate.controller.other;

import com.alibaba.fastjson.JSONObject;
import com.automate.common.annotation.AllowNoLogin;
import com.automate.vcs.git.GitHookHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/22 22:28
 */

@RestController
@RequestMapping("/hook")
public class GitHookController {

    @Autowired
    private GitHookHandler gitHookHandler;

    @AllowNoLogin
    @RequestMapping("/gitHook")
    public String gitHookHandle(@RequestBody JSONObject json, HttpServletRequest request) {
        gitHookHandler.handle(request, json);
        return "success";
    }

}
