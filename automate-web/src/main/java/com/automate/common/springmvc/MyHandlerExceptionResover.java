package com.automate.common.springmvc;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
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
        logger.error("拦截器捕获异常", ex);
        try {
            if (ex instanceof MaxUploadSizeExceededException) {

                response.sendError(500, "上传文件超出最大限制");

            } else if (ex instanceof IllegalArgumentException) {
                response.sendError(500, StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : "参数错误");
            } else {
                response.sendError(500, "内部错误");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView m = new ModelAndView();
        m.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return m;
    }

}
