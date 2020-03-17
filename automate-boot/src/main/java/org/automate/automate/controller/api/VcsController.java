package org.automate.automate.controller.api;

import org.automate.automate.common.ResponseEntity;
import org.automate.automate.contants.VcsType;
import org.springframework.lang.Nullable;
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
@RestController
@RequestMapping("/api/v1")
public class VcsController {

    /**
     * 版本控制测试
     * @param vcsType GIT/SVN
     * @param vcsUrl 地址
     * @param vcsPwd 密码 允许为空
     * @return
     */
    @RequestMapping(value = "/vcs/test", method = RequestMethod.POST)
    public ResponseEntity<Boolean> projectList(
            VcsType vcsType,
            @NotBlank(message = "版本控制地址不能为空") String vcsUrl,
            @Nullable String vcsPwd
    ) {
        return ResponseEntity.ok();
    }

}
