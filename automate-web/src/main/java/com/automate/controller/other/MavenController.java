package com.automate.controller.other;

import com.automate.common.Charsets;
import com.automate.common.SystemConfig;
import com.automate.common.annotation.AllowNoLogin;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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

        logger.debug("【{}】{}", request.getMethod(), request.getServletPath());

        String path = request.getServletPath().substring(6);

        //PUT 是 deploy 上传jar
        if ("PUT".equals(request.getMethod())) {
            //http 基本认证
            String auth = request.getHeader("Authorization");
            String s = null;
            if (StringUtils.isNotBlank(auth)) {
                if(auth.startsWith("Basic ")){
                    auth = auth.substring(6);
                }
                s = new String(Base64.decodeBase64(auth), Charsets.UTF_8);
                //decode后是   用户名:密码  的格式
            }
            if(!"caimao:87677911".equals(s)){
                response.sendError(401, "Unauthorized");
                return null;
            }
            File file = new File(SystemConfig.getMavenRepositoryDir() + path);
            IOUtils.copy(request.getInputStream(), new FileOutputStream(file));
            return "SUCCESS";
        }

        File file = new File(SystemConfig.getMavenRepositoryDir() + path);
        if (file.exists()) {
            if (file.isDirectory()) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return null;
            }
            //返回类型为 二进制
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());

            if (path.toLowerCase().endsWith(".jar")) {
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(), "UTF-8"));
            } else if (path.toLowerCase().endsWith(".war")) {
                response.sendError(403, "war is not allow");
                return null;
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
