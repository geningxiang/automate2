package com.automate.interceptor;

import com.automate.common.SessionUser;
import com.automate.common.annotation.AllowNoLogin;
import com.automate.contants.CommonContants;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/29 23:30
 */
public class AdminInterceptor implements HandlerInterceptor {
    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，类似于try-catch-finally中的finally
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {

    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub
    }

    /**
     * 预处理回调方法，实现处理器的预处理（如登录检查），第三个参数为响应的处理器（如我们上一章的Controller实现）；
     * 返回值： true表示继续流程（如调用下一个拦截器或处理器）；
     * false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(AllowNoLogin.class)) {
                //该方法允许 未登录访问
                return true;
            }

            SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(CommonContants.SESSION_USER_KEY);
            if (sessionUser == null) {

                if (handlerMethod.hasMethodAnnotation(ResponseBody.class) || handlerMethod.getBeanType().isAnnotationPresent(RestController.class)) {
                    //返回数据的情况
                    response.sendError(401, "请登录");
                } else {
                    //跳转到登陆
                    response.sendRedirect("/login");
                }
                return false;
            }
        }
        return true;
    }
}
