package com.automate.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.automate.common.ResponseEntity;
import com.automate.entity.ProjectEntity;
import com.automate.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 23:56
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping("/list")
    public ResponseEntity<JSONArray> list() {
        Iterable<ProjectEntity> list = projectService.findAll();
        JSONArray array = new JSONArray();
        for (ProjectEntity projectEntity : list) {
            array.add(projectEntity.toJson());
        }
        return ResponseEntity.ok(array);
    }
}
