package com.automate.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.automate.entity.HookLogEntity;
import com.automate.service.HookLogService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private HookLogService hookLogService;

    @RequestMapping("/hook")
    public String hook(@RequestBody JSONObject json, HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        JSONObject header = new JSONObject();
        while (headers.hasMoreElements()){
            String h = headers.nextElement();
            header.put(h, request.getHeader(h));
        }
        System.out.println(header.toJSONString());
        System.out.println("##############");
        System.out.println(json.toJSONString());

        //TODO gogs 测试
        HookLogEntity model = new HookLogEntity();
        model.setSource(request.getHeader("user-agent"));

        model.setDelivery(request.getHeader("x-gogs-delivery"));

        model.setRequestHeader(header.toJSONString());

        model.setRequestBody(json.toJSONString());
        model.setHandleStatus((byte) 0);
        hookLogService.save(model);

        return "success";
    }
}
