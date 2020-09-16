package com.automate.common;

import com.automate.contants.CommonContants;
import com.automate.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/7/21 20:27
 */
public class SessionUserManager {

    public static void setSessionUser(HttpServletRequest request, UserEntity userEntity){
        SessionUser sessionUser = new SessionUser(userEntity);
        request.getSession().setAttribute(CommonContants.SESSION_USER_KEY, sessionUser);
    }

    public static void removeSessionUser(HttpServletRequest request){
        request.getSession().removeAttribute(CommonContants.SESSION_USER_KEY);
    }

    public static SessionUser getSessionUser(HttpServletRequest request){
        Object obj = request.getSession().getAttribute(CommonContants.SESSION_USER_KEY);
        if(obj != null && obj instanceof SessionUser){
            return (SessionUser) obj;
        }
        return null;
    }
}
