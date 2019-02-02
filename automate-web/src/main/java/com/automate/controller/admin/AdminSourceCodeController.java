package com.automate.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.automate.controller.BaseController;
import com.automate.entity.HookLogEntity;
import com.automate.entity.SourceCodeBranchEntity;
import com.automate.entity.SourceCodeEntity;
import com.automate.service.HookLogService;
import com.automate.service.SourceCodeBranchService;
import com.automate.service.SourceCodeService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/admin/sourcecode")
public class AdminSourceCodeController extends BaseController {

    @Autowired
    private SourceCodeService sourceCodeService;

    @Autowired
    private SourceCodeBranchService sourceCodeBranchService;

    @Autowired
    private HookLogService hookLogService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, ModelMap modelMap) {
        Iterable<SourceCodeEntity> list = sourceCodeService.findAll();
        modelMap.put("list", Lists.newArrayList(list));
        return "sourcecode/sourcecode_list";
    }

    @RequestMapping("/branchList")
    public String branchList(HttpServletRequest request, ModelMap modelMap) {
        Iterable<SourceCodeBranchEntity> list = sourceCodeBranchService.findAll();
        modelMap.put("list", Lists.newArrayList(list));
        return "sourcecode/sourcecode_branch_list";
    }

    @RequestMapping("/branchDetail")
    public String branchDetail(Integer id, HttpServletRequest request, ModelMap modelMap) {
        JSONArray commitLogs = new JSONArray(0);
        String branchName = "";
        Optional<SourceCodeBranchEntity> sourceCodeBranchEntity = sourceCodeBranchService.getModel(id);
        if(sourceCodeBranchEntity.isPresent()){
            branchName = sourceCodeBranchEntity.get().getBranchName();
            commitLogs = JSONArray.parseArray(sourceCodeBranchEntity.get().getCommitLog());
        }
        modelMap.put("branchName", branchName);
        modelMap.put("commitLogs", commitLogs);
        return "sourcecode/sourcecode_branch_detail";
    }

    @RequestMapping("/hookList")
    public String hookList(Integer pageNo, Integer pageSize, ModelMap modelMap){
        Page<HookLogEntity> pager = hookLogService.findAll(buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id")));
        modelMap.put("pager", pager);
        return "sourcecode/hook_list";
    }

}
