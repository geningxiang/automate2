package com.automate.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.automate.common.ResponseEntity;
import com.automate.entity.ApplicationEntity;
import com.automate.service.ApplicationService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/8 21:32
 */
@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "/application/fileListSha/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<JSONObject> fileListSha(@PathVariable("id") Integer id) {
        if (id == null || id  <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "id is required");
        }
        Optional<ApplicationEntity> applicationEntity = applicationService.findById(id);

        if (!applicationEntity.isPresent()) {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的应用");
        }

        try {
            List<String[]> fileList = ApplicationService.fileSha256List(applicationEntity.get());
            String sha256 = DigestUtils.sha256Hex(JSON.toJSONString(fileList));
            JSONObject data = new JSONObject(2);
            data.put("sha256", sha256);
            data.put("fileList", fileList);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
