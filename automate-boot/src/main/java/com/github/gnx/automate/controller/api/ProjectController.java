package com.github.gnx.automate.controller.api;

import com.github.gnx.automate.common.CurrentUser;
import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.entity.*;
import com.github.gnx.automate.field.req.ReqProjectCreateField;
import com.github.gnx.automate.service.*;
import com.github.gnx.automate.vcs.VcsHelper;
import com.github.gnx.automate.vcs.vo.CommitLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private IProjectBranchService projectBranchService;

    @Autowired
    private IAssemblyLineService assemblyLineService;

    @Autowired
    private IAssemblyLineLogService assemblyLineLogService;

    @Autowired
    private IContainerService containerService;

    @Autowired
    private VcsHelper vcsHelper;

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
    @RequestMapping(value = "/project/{projectId}/assembly_line_logs", method = RequestMethod.GET)
    public ResponseEntity<Page<AssemblyLineLogEntity>> assemblyLineLogList(
            CurrentUser currentUser,
            @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "page", required = false, defaultValue = "20") Integer pageSize
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id"); //创建时间降序排序
        return ResponseEntity.ok(assemblyLineLogService.queryPageByProjectId(projectId, PageRequest.of(page, pageSize, sort)));
    }


    @RequestMapping(value = "/project/{projectId}/fetch", method = RequestMethod.POST)
    public ResponseEntity fetchProject(CurrentUser currentUser, @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId) throws Exception {
        vcsHelper.update(projectService.getModel(projectId).get());
        return ResponseEntity.ok();
    }


    /**
     * 查询指定项目的分支列表
     * @param currentUser
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/branches", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectBranchEntity>> branchList(CurrentUser currentUser, @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId) throws Exception {
        return ResponseEntity.ok(projectBranchService.getList(projectId));
    }

    /**
     * 查询指定项目 指定分支的 vcs提交记录
     * @param currentUser
     * @param projectId
     * @param branch
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/branch/{branch}", method = RequestMethod.GET)
    public ResponseEntity<List<CommitLog>> branchList(CurrentUser currentUser, @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId, @PathVariable("branch") String branch) {
        return null;
    }

    /**
     * 查询指定项目 关联的容器列表
     * @param currentUser
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/containers", method = RequestMethod.GET)
    public ResponseEntity<List<ContainerEntity>> containerList(CurrentUser currentUser, @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId) {
        return ResponseEntity.ok(containerService.getAllByProjectIdOrderById(projectId));
    }


}
