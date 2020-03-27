package com.github.gnx.automate.controller.api;

import com.github.gnx.automate.common.CurrentUser;
import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.ProjectEntity;
import com.github.gnx.automate.field.req.ReqProjectCreateField;
import com.github.gnx.automate.field.rsp.RspContainerField;
import com.github.gnx.automate.service.IAssemblyLineService;
import com.github.gnx.automate.service.IProjectService;
import com.github.gnx.automate.vcs.vo.VcsBranch;
import com.github.gnx.automate.vcs.vo.CommitLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 22:43
 */
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IAssemblyLineService assemblyLineService;

    /**
     * 项目列表
     * @return
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity<Iterable<ProjectEntity>> projectList(CurrentUser currentUser) {
        return ResponseEntity.ok(projectService.findAll());
    }

    /**
     * 创建项目
     * @param reqProjectCreateField
     * @return
     */
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity<ProjectEntity> create(CurrentUser currentUser, @Valid ReqProjectCreateField reqProjectCreateField) {
        return ResponseEntity.ok(projectService.create(reqProjectCreateField, currentUser.getUserId()));
    }

    /**
     * 修改项目
     * @return
     */
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.PATCH)
    public ResponseEntity<ProjectEntity> edit(CurrentUser currentUser, @PathVariable("projectId") Integer projectId, ProjectEntity projectEntity) {

        return null;
    }

    /**
     * 查询单个项目
     * @param currentUser
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<ProjectEntity> detail(CurrentUser currentUser, @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId) {
        Optional<ProjectEntity> projectEntity = projectService.getModel(projectId);
        return ResponseEntity.ok(projectEntity.get());
    }

    /**
     * 查询指定项目的流水线列表
     * @param currentUser
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/assembly_lines", method = RequestMethod.GET)
    public ResponseEntity<List<AssemblyLineEntity>> assemblyLineList(CurrentUser currentUser, @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId) {
        //TODO 权限
        return ResponseEntity.ok(assemblyLineService.getAllByProjectIdOrderById(projectId));
    }

    /**
     * 查询指定项目的 流水线执行记录
     * @param currentUser
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/pipelineLogs", method = RequestMethod.GET)
    public ResponseEntity<List<AssemblyLineLogEntity>> pipelineLogList(CurrentUser currentUser, @PathVariable("projectId") Integer projectId) {
        return null;
    }

    /**
     * 查询指定项目的分支列表
     * @param currentUser
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/branches", method = RequestMethod.GET)
    public ResponseEntity<List<VcsBranch>> branchList(CurrentUser currentUser, @PathVariable("projectId") Integer projectId) {
        return null;
    }

    /**
     * 查询指定项目 指定分支的 vcs提交记录
     * @param currentUser
     * @param projectId
     * @param branch
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/branch/{branch}", method = RequestMethod.GET)
    public ResponseEntity<List<CommitLog>> branchList(CurrentUser currentUser, @PathVariable("projectId") Integer projectId, @PathVariable("branch") String branch) {
        return null;
    }

    /**
     * 查询指定项目 关联的容器列表
     * @param currentUser
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/containers", method = RequestMethod.GET)
    public ResponseEntity<List<RspContainerField>> containerList(CurrentUser currentUser, @PathVariable("projectId") Integer projectId) {
        return null;
    }


}
