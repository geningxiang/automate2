package com.automate.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/29 20:12
 */
@Controller
@RequestMapping("/admin/server")
public class AdminServerController {

    @RequestMapping("/list")
    public String index() {
        return "server/server_list";
    }


}
