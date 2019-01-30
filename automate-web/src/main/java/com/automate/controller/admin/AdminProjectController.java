package com.automate.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.automate.common.ResponseEntity;
import com.automate.entity.ProjectBranchEntity;
import com.automate.entity.ProjectEntity;
import com.automate.service.ProjectBranchService;
import com.automate.service.ProjectService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 23:50
 */
@Controller
@RequestMapping("/admin/project")
public class AdminProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectBranchService projectBranchService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, ModelMap modelMap) {
//        Iterable<ProjectEntity> list = projectService.findAll();
//        modelMap.put("list", Lists.newArrayList(list));
        return "project/project_list";
    }

    @RequestMapping("/branchList")
    public String branchList(HttpServletRequest request, ModelMap modelMap) {
        Iterable<ProjectBranchEntity> list = projectBranchService.findAll();
        modelMap.put("list", Lists.newArrayList(list));
        return "project/project_branch_list";
    }

}
