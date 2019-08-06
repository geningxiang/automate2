package com.automate.controller.api;

import com.automate.common.ResponseEntity;
import com.automate.controller.BaseController;
import com.automate.entity.AssemblyLineEntity;
import com.automate.entity.AssemblyLineLogEntity;
import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.service.AssemblyLineLogService;
import com.automate.service.AssemblyLineService;
import com.automate.service.AssemblyLineTaskLogService;
import com.automate.task.background.BackgroundTaskManager;
import com.automate.task.background.build.BackgroundBuildTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/7 19:41
 */
@RestController
@RequestMapping("/api/assembly")
public class AssemblyController extends BaseController {

    @Autowired
    private AssemblyLineService assemblyLineService;

    @Autowired
    private AssemblyLineLogService assemblyLineLogService;

    @Autowired
    private AssemblyLineTaskLogService assemblyLineTaskLogService;

    @Autowired
    private BackgroundTaskManager backgroundTaskManager;

    /**
     * 流水线列表
     * @param projectId 项目ID
     * @return
     */
    @RequestMapping(value = "/assemblyLines", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity assemblyLineList(Integer projectId) {
        List<AssemblyLineEntity> list;
        if (projectId != null && projectId > 0) {
            list = assemblyLineService.getAllByProjectId(projectId);
        } else {
            list = new ArrayList<>(0);
        }

        return ResponseEntity.ok(list);
    }

    /**
     * 流水线信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/assemblyLine/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<AssemblyLineEntity> assemblyLineDetail(@PathVariable("id") Integer id) {
        if (id != null && id > 0) {
            Optional<AssemblyLineEntity> model = assemblyLineService.getModel(id);
            if (model.isPresent()) {
                return ResponseEntity.ok(model.get());
            }
        }
        return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的流水线");
    }

    /**
     * 保存流水线
     * @param assemblyLineEntity
     * @return
     */
    @RequestMapping(value = "/assemblyLine", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> saveAssemblyLine(AssemblyLineEntity assemblyLineEntity) {
        //缺少了 验证
        if (assemblyLineEntity.getId() != null && assemblyLineEntity.getId() > 0) {
            Optional<AssemblyLineEntity> optional = assemblyLineService.getModel(assemblyLineEntity.getId());
            if (optional.isPresent()) {
                AssemblyLineEntity model = optional.get();
                BeanUtils.copyProperties(assemblyLineEntity, model, "id", "projectId");
                assemblyLineService.save(model);
            }
        } else {
            assemblyLineService.save(assemblyLineEntity);
        }
        return ResponseEntity.ok("创建成功");
    }

    /**
     * 启动一个流水线
     * @param id
     * @param branchName
     * @param commitId
     * @return
     */
    @RequestMapping(value = "/startAssemblyLine", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity startAssemblyLine(Integer id, String branchName, String commitId) {
        if (id == null || id <= 0 || StringUtils.isBlank(branchName)) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        Optional<AssemblyLineEntity> assemblyLineEntity = assemblyLineService.getModel(id);
        if (!assemblyLineEntity.isPresent()) {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的流水线");
        }
        if (!assemblyLineService.isMatch(assemblyLineEntity.get().getBranches(), branchName)) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "分支不匹配:" + branchName + " | " + assemblyLineEntity.get().getBranches());
        }
        try {
            backgroundTaskManager.execute(new BackgroundBuildTask(assemblyLineEntity.get(), branchName, commitId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return ResponseEntity.ok();
    }

    /**
     * 流水线执行记录列表
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/assemblyLineLogs", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity assemblyLineLogList(Integer projectId, Integer pageNo, Integer pageSize) {
        if (projectId == null || projectId <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "projectId is required");
        }
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        AssemblyLineLogEntity condition = new AssemblyLineLogEntity();
        condition.setProjectId(projectId);
        Page<AssemblyLineLogEntity> pager = assemblyLineLogService.findAll(condition, pageRequest);
        return ResponseEntity.ok(pager);
    }

    /**
     * 流水线执行记录明细
     * @param id
     * @return
     */
    @RequestMapping(value = "/assemblyLineLog/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity assemblyLineLogDetail(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        List<AssemblyLineTaskLogEntity> list = assemblyLineTaskLogService.findAllByAssemblyLineLogId(id);
        return ResponseEntity.ok(list);
    }

}
