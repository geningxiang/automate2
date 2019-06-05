package com.automate.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/9 23:33
 */
@Controller
@RequestMapping("/admin/package")
public class AdminPackageController {

    @RequestMapping("/list")
    public String list(ModelMap modelMap) {
        return "package/package_list";
    }

    @RequestMapping("/detail")
    public String detail(Integer id, ModelMap modelMap) {
        modelMap.put("id", id);
        return "package/package_detail";
    }
}
