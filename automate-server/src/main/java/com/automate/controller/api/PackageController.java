package com.automate.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.automate.common.ResponseEntity;
import com.automate.common.SessionUser;
import com.automate.common.SessionUserManager;
import com.automate.controller.BaseController;
import com.automate.entity.ProjectPackageEntity;
import com.automate.service.ProjectPackageService;
import org.apache.commons.codec.digest.DigestUtils;
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

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/packageUpload", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity packageUpload(ProjectPackageEntity model, @RequestParam("fileData") CommonsMultipartFile fileData, HttpServletRequest request) {
        SessionUser sessionUser = SessionUserManager.getSessionUser(request);
        if(sessionUser == null){
            return ResponseEntity.of(HttpStatus.UNAUTHORIZED, "请登录");
        }
        //查询是否有重复
        String sha1 = DigestUtils.sha1Hex(fileData.getBytes());
        ProjectPackageEntity projectPackageEntity = projectPackageService.getFirstBySha1OrderByIdDesc(sha1);
        if(projectPackageEntity != null){
            return ResponseEntity.of(HttpStatus.CONFLICT, "SHA1已存在", projectPackageEntity);
        }
        try {
            model.setUserId(sessionUser.getAdminUser().getId());
            projectPackageService.create(model, fileData);
            return ResponseEntity.ok("上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
