package com.automate.controller.api;

import com.automate.common.ResponseEntity;
import com.automate.controller.BaseController;
import com.automate.entity.ProjectPackageEntity;
import com.automate.service.ProjectPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/9 23:36
 */
@RestController
@RequestMapping("/api/package")
public class PackageController extends BaseController {

    @Autowired
    private ProjectPackageService projectPackageService;

    @RequestMapping(value = "/list")
    public ResponseEntity list(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<ProjectPackageEntity> pager = projectPackageService.findAll(pageRequest);
        return ResponseEntity.ok(pager);
    }
}
