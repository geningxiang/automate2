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
@RequestMapping("/admin/container")
public class AdminContainerController {

    @RequestMapping("/list")
    public String list() {
        return "container/container_list";
    }
}
