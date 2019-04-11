package com.automate.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/31 17:21
 */
@Controller
@RequestMapping("/admin/mvn")
public class AdminMvnController {

    @RequestMapping("/index")
    public String index() {
        return "maven/index";
    }

}
