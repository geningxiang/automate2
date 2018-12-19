package com.automate.controller.admin;

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
@RequestMapping("/admin")
public class MainController {


    @RequestMapping("/index")
    public String index() {
        return "index";
    }


}
