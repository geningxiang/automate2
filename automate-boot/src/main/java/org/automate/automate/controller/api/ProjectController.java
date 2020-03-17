package org.automate.automate.controller.api;

import org.automate.automate.common.CurrentUser;
import org.automate.automate.common.ResponseEntity;
import org.automate.automate.entity.ProjectEntity;
import org.automate.automate.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 22:43
 */
@RestController
@RequestMapping("/api/v1")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    /**
     * 项目列表
     * @return
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity<Iterable<ProjectEntity>> projectList(String userToken) {
        return ResponseEntity.ok(projectService.findAll());
    }

    /**
     * 创建项目
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity<ProjectEntity> create(CurrentUser currentUser, ProjectEntity projectEntity) {
        return null;
    }

    /**
     * 修改项目
     * @return
     */
    @RequestMapping(value = "/project/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<ProjectEntity> edit(CurrentUser currentUser, @PathVariable("id") Integer id, ProjectEntity projectEntity) {
        return null;
    }

}
