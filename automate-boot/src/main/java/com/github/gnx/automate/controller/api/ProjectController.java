package com.github.gnx.automate.controller.api;

import com.github.gnx.automate.common.CurrentUser;
import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.entity.*;
import com.github.gnx.automate.field.req.AssemblyLineSaveField;
import com.github.gnx.automate.field.req.ReqProjectCreateField;
import com.github.gnx.automate.service.*;
import com.github.gnx.automate.vcs.VcsHelper;
import com.github.gnx.automate.vcs.vo.CommitLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
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

    private final IProjectService projectService;

    private final IProjectBranchService projectBranchService;

    private final IAssemblyLineService assemblyLineService;

    private final IAssemblyLineLogService assemblyLineLogService;

    private final IContainerService containerService;

    private final VcsHelper vcsHelper;

    public ProjectController(IProjectService projectService, IProjectBranchService projectBranchService, IAssemblyLineService assemblyLineService, IAssemblyLineLogService assemblyLineLogService, IContainerService containerService, VcsHelper vcsHelper) {
        this.projectService = projectService;
        this.projectBranchService = projectBranchService;
        this.assemblyLineService = assemblyLineService;
        this.assemblyLineLogService = assemblyLineLogService;
        this.containerService = containerService;
        this.vcsHelper = vcsHelper;
    }

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
     * 同步项目的vcs
     * @param currentUser
     * @param projectId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/project/{projectId}/fetch", method = RequestMethod.POST)
    public ResponseEntity fetchProject(CurrentUser currentUser, @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId) throws Exception {
        vcsHelper.update(projectService.getModel(projectId).get());
        // 刷新 分支列表
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


    /**
     * 查询指定项目的流水线列表
     * @param currentUser
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/project/{projectId}/assembly_lines", method = RequestMethod.GET)
    public ResponseEntity<List<AssemblyLineEntity>> assemblyLineList(CurrentUser currentUser, @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId) {
        return ResponseEntity.ok(assemblyLineService.getAllByProjectIdOrderById(projectId));
    }

    /**
     * 创建流水线
     * @param projectId 项目ID
     * @param assemblyLineSaveField 流水线保存对象
     * @return ResponseEntity<AssemblyLineEntity>
     */
    @RequestMapping(value = "/project/{projectId}/assembly_line", method = RequestMethod.POST)
    public ResponseEntity<AssemblyLineEntity> create(
            CurrentUser currentUser,
            @PathVariable("projectId") @NotNull(message = "请输入项目ID") Integer projectId,
            @RequestBody @Validated AssemblyLineSaveField assemblyLineSaveField
    ) {

        return ResponseEntity.ok(assemblyLineService.create(assemblyLineSaveField, projectId, currentUser.getUserId()));
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
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        //倒序排序
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return ResponseEntity.ok(assemblyLineLogService.queryPageByProjectId(projectId, PageRequest.of(page - 1, pageSize, sort)));
    }


    /**
     * 暂时只支持 json格式
     * @param projectId
     * @param token
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/project/hook/{projectId}/{token}")
    public ResponseEntity onHook(@PathVariable("projectId") Integer projectId, @PathVariable("token") String token, HttpServletRequest request) throws IOException {
        System.out.println("[onHook]" + projectId + "\t" + token);

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            System.out.println(headerName + "\t" + request.getHeader(headerName));

        }

        StringBuilder data = new StringBuilder(102400);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data.append(line);
            }
        }

        System.out.println(data.toString());


        return ResponseEntity.ok();

    }

}
