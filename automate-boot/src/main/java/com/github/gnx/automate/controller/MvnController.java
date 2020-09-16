package com.github.gnx.automate.controller;

import com.github.gnx.automate.common.Charsets;
import com.github.gnx.automate.common.SystemUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * Description: 提供maven deploy及下载 服务
 * @author genx
 * @date 2020/3/17 22:48
 */
@RestController
@RequestMapping("/maven")
public class MvnController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 接收 deploy 文件流
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/**", method = RequestMethod.PUT, produces = "text/plain")
    public String put(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("[deploy to private maven repository]{}", request.getServletPath());

        //http 基本认证
        String auth = request.getHeader("Authorization");
        String s = null;
        if (StringUtils.isNotBlank(auth)) {
            if (auth.startsWith("Basic ")) {
                auth = auth.substring(6);
            }
            s = new String(Base64.decodeBase64(auth), Charsets.UTF_8);
            //decode后是   用户名:密码  的格式
        }
        if (!"caimao:87677911".equals(s)) {
            response.sendError(401, "Unauthorized");
            return "Unauthorized";
        }

        //去掉前缀
        String path = request.getServletPath().substring(6);
        File file = new File(SystemUtil.getMvnDeployDir() + path);
        //判断是否存在上级文件夹
        File dir = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator) + 1));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        IOUtils.copy(request.getInputStream(), new FileOutputStream(file));
        logger.debug("copy file {}", file.getAbsolutePath());
        return "SUCCESS";
    }

    /**
     * 提供私有库下载服务
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("[download from private maven repository]{}", request.getServletPath());

        //去掉前缀 /maven
        String path = request.getServletPath().substring(6);

        File file = new File(SystemUtil.getMvnDeployDir() + path);
        if (file.exists()) {
            if (file.isDirectory()) {
                response.sendError(HttpStatus.FORBIDDEN.value());
                return;
            }
            //返回类型为 二进制
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());

            if (path.toLowerCase().endsWith(".jar")) {
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(), "UTF-8"));
            } else if (path.toLowerCase().endsWith(".war")) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), "war is not allow");
                return;
            } else {
                response.setContentType(MediaType.TEXT_PLAIN.getType());
            }
            response.getOutputStream().write(FileUtils.readFileToByteArray(file));
            return;
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value());
        }

    }
}
