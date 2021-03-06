package com.github.gnx.automate.common.webmvc.resolver;

import com.github.gnx.automate.common.CurrentUser;
import com.github.gnx.automate.common.CurrentUserManager;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 22:55
 */
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private CurrentUserManager currentUserManager;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        CurrentUser currentUser = currentUserManager.getCurrentUser(request);
        if (currentUser == null) {

            //TODO 测试阶段
            currentUser = new CurrentUser(1);

//            throw new AuthenticationException();
        }
        return currentUser;
    }

}
