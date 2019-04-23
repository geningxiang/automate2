package com.automate.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.automate.common.ResponseEntity;
import com.automate.controller.BaseController;
import com.automate.entity.HookLogEntity;
import com.automate.entity.ProjectEntity;
import com.automate.entity.ProjectBranchEntity;
import com.automate.service.HookLogService;
import com.automate.service.ProjectService;
import com.automate.service.ProjectBranchService;
import com.automate.task.background.BackgroundLock;
import com.automate.vcs.IVCSHelper;
import com.automate.vcs.TestVCSRepository;
import com.automate.vcs.VCSHelper;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
 *
 * @author: genx
 * @date: 2019/1/30 23:56
 */
@RestController
@RequestMapping("/api")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectBranchService projectBranchService;

    @Autowired
    private HookLogService hookLogService;

    /**
     * 项目列表
     * TODO 暂时没有做分页处理
     *
     * @return
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<JSONArray> list() {
        Iterable<ProjectEntity> list = projectService.findAll();
        JSONArray array = new JSONArray();
        for (ProjectEntity sourceCodeEntity : list) {
            array.add(sourceCodeEntity.toJson());
        }
        return ResponseEntity.ok(array);
    }

    @RequestMapping(value = "/vcsTest", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity testConnection(TestVCSRepository model) {
        try {
            IVCSHelper helper = VCSHelper.create(model);
            helper.testConnetction();
            return ResponseEntity.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity saveSourceCode(ProjectEntity model) {
        Assert.hasText(model.getName(), "项目名称不能为空");
        try {
            //vcs 连接 测试
            IVCSHelper helper = VCSHelper.create(model);
            helper.testConnetction();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        model.setStatus(ProjectEntity.Status.ACTIVATE);
        projectService.save(model);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity sourceCodeDetail(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        Optional<ProjectEntity> sourceCodeEntity = projectService.getModel(id);
        if (sourceCodeEntity.isPresent()) {
            return ResponseEntity.ok(sourceCodeEntity.get().toJson());
        } else {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的项目:id=" + id);
        }
    }

    @RequestMapping(value = "/projectBranches", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<List> branchList(Integer id) {
        List<ProjectBranchEntity> list = projectBranchService.getList(id);
        JSONArray array = new JSONArray(list.size());
        JSONObject json;
        for (ProjectBranchEntity item : list) {
            json = new JSONObject();
            json.put("sourceCodeId", item.getProjectId());
            json.put("branchName", item.getBranchName());
            json.put("lastCommitId", item.getLastCommitId());
            json.put("lastCommitTime", FastDateFormat.getInstance("yyyy-MM-dd HH:mm").format(item.getLastCommitTime()));
            json.put("lastCommitUser", item.getLastCommitUser());
            array.add(json);
        }
        return ResponseEntity.ok(array);
    }

    /**
     * 同步单个代码仓库
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/projectSync",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity sync(Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        Optional<ProjectEntity> sourceCodeEntity = projectService.getModel(id);
        if (!sourceCodeEntity.isPresent()) {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的代码仓库");
        }
        try {
            int updated = BackgroundLock.tryLock("SOURCE_CODE_" + sourceCodeEntity.get().getId(),
                    () -> projectService.sync(sourceCodeEntity.get()));
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.warn("同步代码仓库发生错误", e);
            return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @RequestMapping(value = "/hookList", produces = "application/json;charset=UTF-8")
    public ResponseEntity hookList(Integer pageNo, Integer pageSize) {
        Page<HookLogEntity> pager = hookLogService.findAll(buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id")));
        return ResponseEntity.ok(pager);
    }

}
