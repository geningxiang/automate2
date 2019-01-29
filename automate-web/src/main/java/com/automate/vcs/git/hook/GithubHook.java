package com.automate.vcs.git.hook;

import com.alibaba.fastjson.JSONObject;
import com.automate.vcs.git.AbstractGitHook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/22 23:14
 */
@Component
public class GithubHook extends AbstractGitHook {
    @Override
    public boolean match(HttpServletRequest request, JSONObject data) {
        return false;
    }

    @Override
    public void handle(HttpServletRequest request, JSONObject data) {

    }

    /*
     *  create      创建存储库，分支或标记。
     *  delete      已删除的分支或标记。
     *  label       标签 新建、删除、修改
     *  milestone   里程碑 created，closed，opened，edited，或deleted。
     *
     *
     *  pull_request
     */

}
