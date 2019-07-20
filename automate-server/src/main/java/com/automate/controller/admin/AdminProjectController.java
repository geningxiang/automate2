package com.automate.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.automate.controller.BaseController;
import com.automate.entity.ProjectBranchEntity;
import com.automate.service.ProjectBranchService;
import com.automate.service.ProjectService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 23:50
 */
@Controller
@RequestMapping("/admin/project")
public class AdminProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectBranchService projectBranchService;

    @RequestMapping("/list")
    public String list() {
        return "project/project_list";
    }

    @RequestMapping("/detail")
    public String detail(Integer id, ModelMap modelMap) {
        modelMap.put("id", id);
        return "project/project_detail";
    }

    @RequestMapping("/branchList")
    public String branchList(HttpServletRequest request, ModelMap modelMap) {
        Iterable<ProjectBranchEntity> list = projectBranchService.findAll();
        modelMap.put("list", Lists.newArrayList(list));
        modelMap.put("projectMap", projectService.findAllWidthMap());
        return "project/project_branch_list";
    }

    @RequestMapping("/branchDetail")
    public String branchDetail(Integer id, HttpServletRequest request, ModelMap modelMap) {
        JSONArray commitLogs = new JSONArray(0);
        String branchName = "";
        Optional<ProjectBranchEntity> sourceCodeBranchEntity = projectBranchService.getModel(id);
        if (sourceCodeBranchEntity.isPresent()) {
            branchName = sourceCodeBranchEntity.get().getBranchName();
            commitLogs = JSONArray.parseArray(sourceCodeBranchEntity.get().getCommitLog());
        }
        modelMap.put("branchName", branchName);
        modelMap.put("commitLogs", commitLogs);
        return "project/project_branch_detail";
    }

    @RequestMapping("/hookList")
    public String hookList(Integer pageNo, Integer pageSize, ModelMap modelMap) {
        return "project/hook_list";
    }

}
