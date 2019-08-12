package com.automate.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.automate.common.ResponseEntity;
import com.automate.entity.ApplicationEntity;
import com.automate.exec.ExecCommand;
import com.automate.service.ApplicationService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
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

    /**
     * 返指定应用的文件列表
     * @param id
     * @return
     * {
     *   "data": {
     *     "sha256": "080c1cbbcd9662e4ddd6c8826806dab79cece58edc12c781f982bdf0b158172d",
     *     "fileList": [
     *       [
     *         "WEB-INF/classes/com/automate/build/IBuildHelper.class",
     *         "4a056dd2ed3d981bcde3226a1fc8acb4f48f350b28100387114b4cde345a3422"
     *       ],
     *       [
     *         "WEB-INF/classes/com/automate/build/MyCat.class",
     *         "425743e33ec7799109be0c3a7183f26cf89f81f02c51a62247530f10d7b64f86"
     *       ],...
     *     ]
     *   },
     *   "msg": "",
     *   "status": 200,
     *   "timestamp": 1565614463109
     * }
     */
    @RequestMapping(value = "/application/fileListSha/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<JSONObject> fileListSha(@PathVariable("id") Integer id) {
        if (id == null || id  <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "id is required");
        }
        //TODO 添加权限校验
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

    @RequestMapping(value = "/application", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<JSONObject> create(ApplicationEntity applicationEntity) {
        //TODO 参数校验
        applicationService.save(applicationEntity);
        return ResponseEntity.ok(applicationEntity.toJson());
    }

    @RequestMapping(value = "/application/{id}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<JSONObject> get(@PathVariable("id") Integer id) {
        Assert.notNull(id, "id 不能为空");
        Optional<ApplicationEntity> applicationEntity = applicationService.findById(id);
        if(applicationEntity.isPresent()){
            return ResponseEntity.ok(applicationEntity.get().toJson());
        } else {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的应用");
        }
    }

    @RequestMapping(value = "/application/{id}/stop", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> stop(@PathVariable("id") Integer id) {
        Assert.notNull(id, "id 不能为空");
        Optional<ApplicationEntity> applicationEntity = applicationService.findById(id);
        if(applicationEntity.isPresent()){
            try {
                ExecCommand execCommand = ApplicationService.containerStop(applicationEntity.get());
                if(execCommand.getExitValue() == 0){
                    return ResponseEntity.ok(execCommand.getOut().toString());
                } else {
                    return ResponseEntity.of(execCommand.getExitValue(), "应用关闭失败", execCommand.getOut().toString());
                }

            } catch (Exception e) {
                return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionUtils.getStackTrace(e));
            }

        } else {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的应用");
        }
    }

    @RequestMapping(value = "/application/{id}/start", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> start(@PathVariable("id") Integer id) {
        Assert.notNull(id, "id 不能为空");
        Optional<ApplicationEntity> applicationEntity = applicationService.findById(id);
        if(applicationEntity.isPresent()){
            try {
                ExecCommand execCommand = ApplicationService.containerStart(applicationEntity.get());
                if(execCommand.getExitValue() == 0){
                    return ResponseEntity.ok(execCommand.getOut().toString());
                } else {
                    return ResponseEntity.of(execCommand.getExitValue(), "应用关闭失败", execCommand.getOut().toString());
                }

            } catch (Exception e) {
                return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionUtils.getStackTrace(e));
            }

        } else {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的应用");
        }
    }

}
