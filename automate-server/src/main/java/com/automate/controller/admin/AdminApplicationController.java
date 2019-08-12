package com.automate.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/27 21:51
 */
@Controller
@RequestMapping("/admin/application")
public class AdminApplicationController {

    @RequestMapping("/list")
    public String list() {
        return "application/application_list";
    }

    @RequestMapping("/updateApplyList")
    public String updateApplyList() {
        return "application/application_update_apply_list";
    }

    @RequestMapping("/updateLogList")
    public String updateLogList() {
        return "application/application_update_log_list";
    }
}
