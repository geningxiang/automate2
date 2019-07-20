package com.automate.controller.api;

import com.alibaba.fastjson.JSON;
import com.automate.common.ResponseEntity;
import com.automate.controller.BaseController;
import com.automate.entity.ProjectPackageEntity;
import com.automate.service.ProjectPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/9 23:36
 */
@RestController
@RequestMapping("/api")
public class PackageController extends BaseController {

    @Autowired
    private ProjectPackageService projectPackageService;

    /**
     * 包列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/projectPackages", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity list(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<ProjectPackageEntity> pager = projectPackageService.findAll(pageRequest);
        return ResponseEntity.ok(pager);
    }

    @RequestMapping(value = "/projectPackage" , method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity save(ProjectPackageEntity model, String remark, @RequestParam("fileData") CommonsMultipartFile fileData) {
        try {
            System.out.println(JSON.toJSONString(model));
            System.out.println(remark);
            projectPackageService.create(model, fileData);
            return ResponseEntity.ok("保存成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


    }
}
