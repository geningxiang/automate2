package com.automate.controller.admin;

import com.automate.controller.BaseController;
import com.automate.entity.AssemblyLineEntity;
import com.automate.entity.AssemblyLineLogEntity;
import com.automate.service.AssemblyLineLogService;
import com.automate.service.AssemblyLineService;
import com.automate.service.ProjectService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/7 19:41
 */
@Controller
@RequestMapping("/admin/assembly")
public class AdminAssemblyController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AssemblyLineService assemblyLineService;

    @Autowired
    private AssemblyLineLogService assemblyLineLogService;


    @RequestMapping("/list")
    public String list(ModelMap modelMap) {
        Iterable<AssemblyLineEntity> list = assemblyLineService.findAll();
        modelMap.put("list", Lists.newArrayList(list));
        modelMap.put("sourceCodeMap", projectService.findAllWidthMap());
        return "assembly/assembly_list";
    }

    @RequestMapping("/detail")
    public String detail(Integer id, Integer projectId,  ModelMap modelMap) {
        AssemblyLineEntity assemblyLineEntity = null;
        if (id != null && id > 0) {
            Optional<AssemblyLineEntity> model = assemblyLineService.getModel(id);
            if (model.isPresent()) {
                assemblyLineEntity = model.get();
            }
        }
        if (assemblyLineEntity == null) {
            assemblyLineEntity = new AssemblyLineEntity();
            assemblyLineEntity.setProjectId(projectId);
        }
        modelMap.put("assemblyLineEntity", assemblyLineEntity);
        return "assembly/assembly_detail";
    }

    @RequestMapping("/assemblyLoglist")
    public String assemblyLoglist(Integer pageNo, Integer pageSize, ModelMap modelMap) {
        Page<AssemblyLineLogEntity> pager = assemblyLineLogService.findAll(buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id")));
        modelMap.put("pager", pager);
        modelMap.put("assemblyLineMap", assemblyLineService.findAllWidthMap());
        modelMap.put("sourceCodeMap", projectService.findAllWidthMap());
        return "assembly/assembly_log_list";
    }

    @RequestMapping("/assemblyLogDetail")
    public String assemblyLogDetail(Integer id, ModelMap modelMap) {
        modelMap.put("id", id);
        return "assembly/assembly_log_detail";
    }
}
