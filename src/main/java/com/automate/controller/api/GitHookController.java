package com.automate.controller.api;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/22 22:28
 */

@RestController
@RequestMapping("/api/git")
public class GitHookController {

    @RequestMapping("/hook")
    public String hook(@RequestBody JSONObject json, HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()){
            String h = headers.nextElement();
            System.out.println(h);
            System.out.println(request.getHeader(h));
        }

        System.out.println("##############");
        System.out.println(json.toJSONString());
        return "success";
    }
}
