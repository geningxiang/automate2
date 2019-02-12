package com.automate.controller.admin;

import com.automate.entity.AssemblyLineEntity;
import com.automate.service.AssemblyLineService;
import com.automate.service.SourceCodeService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminAssemblyController {

    @Autowired
    private SourceCodeService sourceCodeService;

    @Autowired
    private AssemblyLineService assemblyLineService;


    @RequestMapping("/list")
    public String list(ModelMap modelMap) {
        Iterable<AssemblyLineEntity> list = assemblyLineService.findAll();
        modelMap.put("list", Lists.newArrayList(list));
        modelMap.put("sourceCodeMap", sourceCodeService.findAllWidthMap());
        return "assembly/assembly_list";
    }

    @RequestMapping("/detail")
    public String detail(Integer id, ModelMap modelMap) {
        AssemblyLineEntity assemblyLineEntity = null;
        if(id != null && id > 0){
            Optional<AssemblyLineEntity> model = assemblyLineService.getModel(id);
            if(model.isPresent()){
                assemblyLineEntity = model.get();
            }
        }
        if(assemblyLineEntity == null){
            assemblyLineEntity = new AssemblyLineEntity();
        }
        modelMap.put("assemblyLineEntity", assemblyLineEntity);
        return "assembly/assembly_detail";
    }
}
