package com.automate.common.springmvc;

import com.alibaba.fastjson.JSON;
import com.automate.common.ResponseEntity;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @className:MyHandlerExceptionResover.java
 * @classDescription: 全局的异常处理 并记录logger
 * @author:genx
 * @createTime:2017年5月25日 下午3:36:41
 */
@Component
public class MyHandlerExceptionResover implements HandlerExceptionResolver {
    private static Logger logger = LoggerFactory.getLogger(MyHandlerExceptionResover.class);


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //把异常信息记入日志
        logger.error("捕获异常", ex);

        if (ex instanceof NullPointerException) {
            return sendError(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionUtils.getStackTrace(ex), response, handler);
        } else if (ex instanceof MaxUploadSizeExceededException) {
            return sendError(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, ex.getMessage(), response, handler);
        } else if (ex instanceof IllegalArgumentException) {
            return sendError(HttpStatus.BAD_REQUEST, ex.getMessage(), response, handler);
        } else {
            return sendError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), response, handler);
        }
    }

    private boolean isResponseBody(Object handler) {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            if (((HandlerMethod) handler).hasMethodAnnotation(ResponseBody.class)) {
                //logger.debug("方法中包含@ResponseBody");
                return true;
            } else if (((HandlerMethod) handler).getBeanType().isAnnotationPresent(RestController.class)) {
                //logger.debug("Controller包含@RestController");
                return true;
            }
        }
        return false;
    }

    private ModelAndView sendError(HttpStatus status, String msg, HttpServletResponse response, Object handler) {
        if (isResponseBody(handler)) {
            try {
                //response.setStatus(HttpStatus.OK.value());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write(JSON.toJSONString(ResponseEntity.of(status, msg)));
                ModelAndView m = new ModelAndView();
                m.setStatus(HttpStatus.OK);
                return m;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            response.sendError(status.value(), msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView m = new ModelAndView();
        m.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return m;
    }


}
