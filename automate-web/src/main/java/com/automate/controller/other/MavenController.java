package com.automate.controller.other;

import com.automate.common.SystemConfig;
import com.automate.common.annotation.AllowNoLogin;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * maven文件夹的代理
 * 主要是需要计算 .sha1, .md5
 *
 * @author: genx
 * @date: 2019/1/30 11:10
 */
@Controller
@RequestMapping("/maven")
public class MavenController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DOT_SHA1 = ".sha1";

    private static final String DOT_MD5 = ".md5";


    /**
     * @return
     */
    @ResponseBody
    @AllowNoLogin
    @RequestMapping("/**")
    public String proxy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug(request.getServletPath());

        String path = request.getServletPath().substring(6);
        File file = new File(SystemConfig.getMavenRepositoryDir() + path);
        if (file.exists()) {
            if(file.isDirectory()){
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return null;
            }
            //返回类型为 二进制
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());

            if(path.toLowerCase().endsWith(".jar") || path.toLowerCase().endsWith(".war")) {
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(), "UTF-8"));
            } else {
                response.setContentType(MediaType.TEXT_PLAIN.getType());
            }
            response.getOutputStream().write(FileUtils.readFileToByteArray(file));
            return null;
        } else if (path.toLowerCase().endsWith(DOT_SHA1)) {
            path = path.substring(0, path.length() - DOT_SHA1.length());
            file = new File(SystemConfig.getMavenRepositoryDir() + path);
            if (file.exists()) {
                return sendText(response, DigestUtils.sha1Hex(new FileInputStream(file)));
            }
        } else if (path.toLowerCase().endsWith(DOT_MD5)) {
            path = path.substring(0, path.length() - DOT_MD5.length());
            file = new File(SystemConfig.getMavenRepositoryDir() + path);
            if (file.exists()) {
                return sendText(response, DigestUtils.md5Hex(new FileInputStream(file)));
            }
        }

        response.sendError(404);
        return null;
    }

    private String sendText(HttpServletResponse response, @NonNull String text) throws IOException {
        response.setContentType(MediaType.TEXT_PLAIN.getType());
        response.getOutputStream().write(text.getBytes());
        return null;
    }
}
