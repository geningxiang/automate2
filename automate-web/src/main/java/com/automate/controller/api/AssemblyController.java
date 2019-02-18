package com.automate.controller.api;

import com.automate.common.ResponseEntity;
import com.automate.controller.BaseController;
import com.automate.entity.AssemblyLineEntity;
import com.automate.entity.AssemblyLineLogEntity;
import com.automate.entity.AssemblyLineTaskLogEntity;
import com.automate.service.AssemblyLineLogService;
import com.automate.service.AssemblyLineService;
import com.automate.service.AssemblyLineTaskLogService;
import com.automate.task.background.BackgroundAssemblyManager;
import com.automate.task.background.impl.BaseSourceCodeAssembly;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    private BackgroundAssemblyManager backgroundAssemblyManager;


    @RequestMapping(value = "/list")
    public ResponseEntity list(Integer sourceCodeId) {
        List<AssemblyLineEntity> list;
        if (sourceCodeId != null && sourceCodeId > 0) {
            list = assemblyLineService.getAllBySourceCodeId(sourceCodeId);
        } else {
            list = new ArrayList<>(0);
        }

        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/detail")
    public ResponseEntity<AssemblyLineEntity> detail(Integer id) {
        if (id != null && id > 0) {
            Optional<AssemblyLineEntity> model = assemblyLineService.getModel(id);
            if (model.isPresent()) {
                return ResponseEntity.ok(model.get());
            }
        }
        return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的流水线");
    }

    @RequestMapping(value = "/assemblyLine", method = RequestMethod.POST)
    public ResponseEntity<String> createAssemblyLine(AssemblyLineEntity assemblyLineEntity) {

        //缺少了 验证
        if (assemblyLineEntity.getId() != null && assemblyLineEntity.getId() > 0) {
            Optional<AssemblyLineEntity> optional = assemblyLineService.getModel(assemblyLineEntity.getId());
            if (optional.isPresent()) {
                AssemblyLineEntity model = optional.get();
                BeanUtils.copyProperties(assemblyLineEntity, model, "id");
                assemblyLineService.save(model);
            }
        } else {
            assemblyLineService.save(assemblyLineEntity);
        }

        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/start")
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
            backgroundAssemblyManager.execute(BaseSourceCodeAssembly.create(assemblyLineEntity.get(), branchName, commitId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return ResponseEntity.ok();
    }

    @RequestMapping(value = "/logList")
    public ResponseEntity logList(Integer sourceCodeId, Integer pageNo, Integer pageSize) {
        if (sourceCodeId == null || sourceCodeId <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        Page<AssemblyLineLogEntity> pager = assemblyLineLogService.findAll(buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id")));
        return ResponseEntity.ok(pager);
    }

    @RequestMapping(value = "/logDetail")
    public ResponseEntity logDetail(Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        List<AssemblyLineTaskLogEntity> list = assemblyLineTaskLogService.findAllByAssemblyLineLogId(id);
        return ResponseEntity.ok(list);
    }

}
