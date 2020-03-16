package org.automate.automate.controller;

import org.automate.automate.common.ResponseEntity;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

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
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }

        logger.error("全局异常捕获", ex);
        return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, "服务器处理时发生异常");
    }

}
