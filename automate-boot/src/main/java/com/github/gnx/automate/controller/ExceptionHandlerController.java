package com.github.gnx.automate.controller;

import com.github.gnx.automate.common.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/16 23:24
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handle(final Exception ex, final WebRequest req) {
        //参数错误
        if (ex instanceof BindException) {
            List<ObjectError> list = ((BindException) ex).getAllErrors();
            if (list.size() > 0) {
                return ResponseEntity.of(HttpStatus.BAD_REQUEST, list.get(0).getDefaultMessage());
            }
        } else if (ex instanceof MethodArgumentNotValidException) {
            BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<ObjectError> list = result.getAllErrors();
            if (list.size() > 0) {
                return ResponseEntity.of(HttpStatus.BAD_REQUEST, list.get(0).getDefaultMessage());
            }
        }


        if (ex instanceof MaxUploadSizeExceededException) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "上传文件大小超过限制");
        } else if (ex instanceof IllegalArgumentException) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, ex.getMessage());
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, ((MethodArgumentTypeMismatchException) ex).getName() + "参数错误");
        } else if (ex instanceof AuthenticationException) {
            return ResponseEntity.of(HttpStatus.UNAUTHORIZED, "请登录");
        } else if (ex instanceof NoSuchElementException) {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应资源");
        }

        logger.error("全局异常捕获", ex);
        return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}
