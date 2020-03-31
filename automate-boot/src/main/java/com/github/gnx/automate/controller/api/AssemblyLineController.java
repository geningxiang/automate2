package com.github.gnx.automate.controller.api;

import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.service.IAssemblyLineService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/31 21:00
 */
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class AssemblyLineController {

    private final IAssemblyLineService assemblyLineService;

    public AssemblyLineController(IAssemblyLineService assemblyLineService) {
        this.assemblyLineService = assemblyLineService;
    }


    @RequestMapping(value = "/assembly_line/{assemblyLineId}", method = RequestMethod.GET)
    public ResponseEntity<AssemblyLineEntity> getAssemblyLineById(
            @PathVariable("assemblyLineId") @NotNull(message = "请输入流水线ID") Integer assemblyLineId
    ) {
        return ResponseEntity.ok(assemblyLineService.findById(assemblyLineId).get());
    }


}
