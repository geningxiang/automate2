package com.automate.controller.api;

import com.automate.common.ResponseEntity;
import com.automate.entity.UserEntity;
import com.automate.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public ResponseEntity login(String userName, String passWord) {
        UserEntity userEntity = userService.findFirstByUserName(userName);
        if(userEntity == null){
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "用户名或密码错误");
        }
        DigestUtils.md5Hex(userEntity.getPassWord());
        return ResponseEntity.ok(null);
    }
}
