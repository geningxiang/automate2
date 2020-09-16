package com.github.gnx.automate.common;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 23:00
 */
@Component
public class CurrentUserManager {


    public CurrentUser getCurrentUser(HttpServletRequest request) {
        return (CurrentUser) request.getSession().getAttribute("currentUser");
    }

    public void setCurrentUser(CurrentUser currentUser, HttpServletRequest request) {
        request.getSession().setAttribute("currentUser", currentUser);
    }

}
