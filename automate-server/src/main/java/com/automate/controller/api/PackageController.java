package com.automate.controller.api;

import com.automate.common.ResponseEntity;
import com.automate.controller.BaseController;
import com.automate.entity.ApplicationPackageEntity;
import com.automate.service.ApplicationPackageService;
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
    private ApplicationPackageService applicationPackageService;

    @RequestMapping(value = "/list")
    public ResponseEntity list(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Page<ApplicationPackageEntity> pager = applicationPackageService.findAll(pageRequest);
        return ResponseEntity.ok(pager);
    }
}
