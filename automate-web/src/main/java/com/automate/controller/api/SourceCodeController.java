package com.automate.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.automate.common.ResponseEntity;
import com.automate.entity.SourceCodeEntity;
import com.automate.service.SourceCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 23:56
 */
@RestController
@RequestMapping("/api/sourcecode")
public class SourceCodeController {
    @Autowired
    private SourceCodeService sourceCodeService;

    @RequestMapping("/list")
    public ResponseEntity<JSONArray> list() {
        Iterable<SourceCodeEntity> list = sourceCodeService.findAll();
        JSONArray array = new JSONArray();
        for (SourceCodeEntity sourceCodeEntity : list) {
            array.add(sourceCodeEntity.toJson());
        }
        return ResponseEntity.ok(array);
    }

    @RequestMapping("/sync")
    public ResponseEntity sync(Integer id) {
        if(id == null || id <= 0){
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        Optional<SourceCodeEntity> sourceCodeEntity = sourceCodeService.getModel(id);
        if(!sourceCodeEntity.isPresent()){
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的代码仓库");
        }
        int updated = 0;
        try {
            updated = sourceCodeService.sync(sourceCodeEntity.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(updated);
    }

}
