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

    /**
     * 更新包列表界面
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String list(ModelMap modelMap) {
        return "package/package_list";
    }

    /**
     * 更新包上传界面
     * @param modelMap
     * @return
     */
    @RequestMapping("/uploadView")
    public String uploadView(ModelMap modelMap) {
        return "package/package_upload";
    }

    /**
     * 更新包详情界面
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/detail")
    public String detail(Integer id, ModelMap modelMap) {
        modelMap.put("id", id);
        return "package/package_upload";
    }
}
