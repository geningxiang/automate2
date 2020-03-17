package org.automate.automate.controller.api;

import org.apache.commons.codec.digest.DigestUtils;
import org.automate.automate.common.CurrentUser;
import org.automate.automate.common.CurrentUserManager;
import org.automate.automate.common.ResponseEntity;
import org.automate.automate.entity.UserEntity;
import org.automate.automate.field.req.ReqUserField;
import org.automate.automate.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/16 22:51
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private CurrentUserManager currentUserManager;

    /**
     * 登录
     * @param userName 用户名/手机号码/邮箱
     * @param passWord 密码
     * @return
     */
    @RequestMapping("/login")
    public ResponseEntity<String> login(
            @NotBlank(message = "请输入用户名或手机号码") String userName,
            @NotBlank(message = "请输入密码") String passWord,
            HttpServletRequest request
    ) {
        UserEntity userEntity = userService.findNormalUserByKey(userName);
        if (userEntity == null) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "用户名或密码错误");
        }
        if (!DigestUtils.md5Hex(passWord).equals(userEntity.getPassWord())) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "用户名或密码错误");
        }
        currentUserManager.setCurrentUser(new CurrentUser(), request);
        return ResponseEntity.ok("");
    }

    /**
     * 用户列表
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> userList(CurrentUser currentUser) {
        return ResponseEntity.ok();
    }

    /**
     * 创建用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<UserEntity> create(CurrentUser currentUser, ReqUserField user) {
        return null;
    }

    /**
     * 修改用户信息
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<UserEntity> edit(CurrentUser currentUser, @PathVariable("id") Integer id, ReqUserField user) {
        return null;
    }
}
