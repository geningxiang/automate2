package com.automate.controller;

import com.automate.common.ResponseEntity;
import com.automate.common.SessionUser;
import com.automate.common.annotation.AllowNoLogin;
import com.automate.contants.CommonContants;
import com.automate.entity.UserEntity;
import com.automate.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/19 22:57
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @AllowNoLogin
    @RequestMapping(value={"/", "/login"})
    public String login() {
        return "login";
    }

    /**
     * 登陆操作
     * @param userName
     * @param passWord
     * @param request
     * @return
     */
    @ResponseBody
    @AllowNoLogin
    @RequestMapping("doLogin")
    public ResponseEntity doLogin(String userName, String passWord, HttpServletRequest request) {
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)){
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }

        UserEntity userEntity = userService.findFirstByUserName(userName);
        if(userEntity == null) {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "请输入正确的用户名");
        }

        if(!DigestUtils.md5Hex(passWord).equals(userEntity.getPassWord())) {
            return ResponseEntity.of(HttpStatus.FORBIDDEN, "请输入正确的密码");
        }
        SessionUser sessionUser = new SessionUser(userEntity);
        request.getSession().setAttribute(CommonContants.SESSION_USER_KEY, sessionUser);
        return ResponseEntity.ok(null);
    }


    /**
     * 登出操作
     * @return
     */
    @AllowNoLogin
    @RequestMapping("loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute(CommonContants.SESSION_USER_KEY);
        return "login";
    }

    /**
     * 首页
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap modelMap) {
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CommonContants.SESSION_USER_KEY);
        if(sessionUser == null){
            return "login";
        }
        //用户
        modelMap.put("adminUser", sessionUser.getAdminUser());

        return "index";
    }
}
