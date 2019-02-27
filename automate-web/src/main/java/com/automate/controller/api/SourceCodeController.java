package com.automate.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.automate.common.ResponseEntity;
import com.automate.controller.BaseController;
import com.automate.entity.HookLogEntity;
import com.automate.entity.SourceCodeBranchEntity;
import com.automate.entity.SourceCodeEntity;
import com.automate.service.HookLogService;
import com.automate.service.SourceCodeBranchService;
import com.automate.service.SourceCodeService;
import com.automate.vcs.IVCSHelper;
import com.automate.vcs.TestVCSRepository;
import com.automate.vcs.VCSHelper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.LoadingCache;
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
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/30 23:56
 */
@RestController
@RequestMapping("/api/sourcecode")
public class SourceCodeController extends BaseController {

    @Autowired
    private SourceCodeService sourceCodeService;

    @Autowired
    private SourceCodeBranchService sourceCodeBranchService;

    @Autowired
    private HookLogService hookLogService;

    /**
     * 代码仓库列表
     *
     * @return
     */
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public ResponseEntity<JSONArray> list() {
        Iterable<SourceCodeEntity> list = sourceCodeService.findAll();
        JSONArray array = new JSONArray();
        for (SourceCodeEntity sourceCodeEntity : list) {
            array.add(sourceCodeEntity.toJson());
        }
        return ResponseEntity.ok(array);
    }

    @RequestMapping(value = "/testConnection", produces = "application/json;charset=UTF-8")
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

    @RequestMapping(value = "/sourceCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity saveSourceCode(SourceCodeEntity model) {
        Assert.hasText(model.getName(), "项目名称不能为空");
        //vcs地址测试
        try {
            IVCSHelper helper = VCSHelper.create(model);
            helper.testConnetction();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        model.setStatus(SourceCodeEntity.Status.ACTIVATE);
        sourceCodeService.save(model);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/sourceCode/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity sourceCodeDetail(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        Optional<SourceCodeEntity> sourceCodeEntity = sourceCodeService.getModel(id);
        if (sourceCodeEntity.isPresent()) {
            return ResponseEntity.ok(sourceCodeEntity.get().toJson());
        } else {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的项目:id=" + id);
        }
    }

    @RequestMapping(value = "/branchList", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List> branchList(Integer id) {
        List<SourceCodeBranchEntity> list = sourceCodeBranchService.getList(id);
        JSONArray array = new JSONArray(list.size());
        JSONObject json;
        for (SourceCodeBranchEntity item : list) {
            json = new JSONObject();
            json.put("sourceCodeId", item.getSourceCodeId());
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
    @RequestMapping(value = "/sync", produces = "application/json;charset=UTF-8")
    public ResponseEntity sync(Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        Optional<SourceCodeEntity> sourceCodeEntity = sourceCodeService.getModel(id);
        if (!sourceCodeEntity.isPresent()) {
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

    @RequestMapping(value = "/hookList", produces = "application/json;charset=UTF-8")
    public ResponseEntity hookList(Integer pageNo, Integer pageSize) {
        Page<HookLogEntity> pager = hookLogService.findAll(buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id")));
        return ResponseEntity.ok(pager);
    }

}
