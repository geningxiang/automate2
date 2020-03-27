package com.github.gnx.automate.controller.api;

import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.contants.VcsType;
import com.github.gnx.automate.vcs.VcsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 23:05
 */
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class VcsController {

    @Autowired
    VcsHelper vcsHelper;

    /**
     * 版本控制测试
     * @param vcsType GIT/SVN
     * @param vcsUrl 地址
     * @param vcsUserName 用户名 允许空
     * @param vcsPassWord 密码 允许为空
     * @return
     */
    @RequestMapping(value = "/vcs/test", method = RequestMethod.POST)
    public ResponseEntity projectList(
            VcsType vcsType,
            @NotBlank(message = "版本控制地址不能为空") String vcsUrl,
            @Nullable String vcsUserName,
            @Nullable String vcsPassWord
    ) throws Exception {

        vcsHelper.test(vcsType, vcsUrl, vcsUserName, vcsPassWord);
        return ResponseEntity.ok();

    }

}
