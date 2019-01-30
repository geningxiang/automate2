package com.automate.controller.other;

import com.alibaba.fastjson.JSONObject;
import com.automate.entity.HookLogEntity;
import com.automate.service.HookLogService;
import com.automate.vcs.git.GitHookHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api/git")
public class GitHookController {

    @Autowired
    private GitHookHandler gitHookHandler;

    @RequestMapping("/hook")
    public String hook(@RequestBody JSONObject json, HttpServletRequest request) {

        gitHookHandler.handle(request, json);

        return "success";
    }
}
