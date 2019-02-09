package com.automate.controller.admin;

import com.automate.service.AssemblyLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/7 19:41
 */
@Controller
@RequestMapping("/admin/assembly")
public class AdminAssemblyController {

    @Autowired
    private AssemblyLineService assemblyLineService;


    @RequestMapping("/list")
    public String list(ModelMap modelMap) {

        return "assembly/assembly_list";
    }

    @RequestMapping("/detail")
    public String detail(Integer id, ModelMap modelMap) {
        modelMap.put("id", id);
        return "assembly/assembly_detail";
    }
}
