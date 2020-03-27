package com.github.gnx.automate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 22:48
 */
@RestController
@RequestMapping("/mvn")
public class MvnController {

    /**
     * 接收 deploy 文件流
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/**", method = RequestMethod.PUT)
    public String put(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "";
    }

    /**
     * 提供私有库下载服务
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public String get(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "";
    }
}
