package com.automate.controller.api;

import com.automate.common.ResponseEntity;
import com.automate.common.SessionUser;
import com.automate.common.SessionUserManager;
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
    public ResponseEntity packageUpload(Integer projectId, String version, String branch, String remark, Integer type, @RequestParam("fileData") CommonsMultipartFile fileData, HttpServletRequest request) {
        SessionUser sessionUser = SessionUserManager.getSessionUser(request);
        if (sessionUser == null) {
            return ResponseEntity.of(HttpStatus.UNAUTHORIZED, "请登录");
        }

        ProjectPackageEntity.Type t = ProjectPackageEntity.Type.valueOf(type);
        if (t == ProjectPackageEntity.Type.UNKNOWN) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "请指定更新类型");
        }
        try {

            projectPackageService.createByUpload(projectId, version, branch, "", remark, fileData, t, sessionUser.getAdminUser().getId());
            return ResponseEntity.ok("上传成功", null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
