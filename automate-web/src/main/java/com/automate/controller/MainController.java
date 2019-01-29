package com.automate.controller;

import com.automate.common.annotation.AllowNoLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/19 22:57
 */
@Controller
@RequestMapping("/")
public class MainController {

    @AllowNoLogin
    @RequestMapping(value={"/", "/login"})
    public String login() {
        return "login";
    }

    @AllowNoLogin
    @RequestMapping("doLogin")
    public String doLogin() {
        return "index";
    }

    @AllowNoLogin
    @RequestMapping("loginOut")
    public String loginOut() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
