package org.automate.automate.controller.api;

import org.apache.commons.codec.digest.DigestUtils;
import org.automate.automate.common.ResponseEntity;
import org.automate.automate.entity.UserEntity;
import org.automate.automate.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/16 22:51
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录
     * @param userName
     * @param passWord
     * @return
     */
    @RequestMapping("/login")
    public ResponseEntity<UserEntity> login(
            @NotBlank(message = "请输入用户名或手机号码") String userName,
            @NotBlank(message = "请输入密码") String passWord) {
        UserEntity userEntity = userService.findFirstByUserName(userName);
        if (userEntity == null) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "用户名或密码错误");
        }
        if (!DigestUtils.md5Hex(passWord).equals(userEntity.getPassWord())) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "用户名或密码错误");
        }
        return ResponseEntity.ok(userEntity);
    }
}
