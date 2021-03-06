package com.github.gnx.automate.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.github.gnx.automate.assemblyline.AssemblyLineTaskManager;
import com.github.gnx.automate.assemblyline.config.AssemblyLineStepTask;
import com.github.gnx.automate.cache.RunningCacheManager;
import com.github.gnx.automate.common.CurrentUser;
import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.entity.AssemblyLineTaskLogEntity;
import com.github.gnx.automate.field.req.AssemblyLineSaveField;
import com.github.gnx.automate.service.IAssemblyLineLogService;
import com.github.gnx.automate.service.IAssemblyLineService;
import com.github.gnx.automate.service.IAssemblyLineTaskLogService;
import com.github.gnx.automate.vo.request.NewestLogRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/31 21:00
 */
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class AssemblyLineController {

    private final IAssemblyLineService assemblyLineService;

    private final IAssemblyLineLogService assemblyLineLogService;

    private final IAssemblyLineTaskLogService assemblyLineTaskLogService;

    private final AssemblyLineTaskManager assemblyLineTaskManager;

    public AssemblyLineController(IAssemblyLineService assemblyLineService, IAssemblyLineLogService assemblyLineLogService, IAssemblyLineTaskLogService assemblyLineTaskLogService, AssemblyLineTaskManager assemblyLineTaskManager) {
        this.assemblyLineService = assemblyLineService;
        this.assemblyLineLogService = assemblyLineLogService;
        this.assemblyLineTaskLogService = assemblyLineTaskLogService;
        this.assemblyLineTaskManager = assemblyLineTaskManager;
    }


    /**
     * 查询指定流水线信息
     * @param assemblyLineId 流水线ID
     */
    @RequestMapping(value = "/assembly_line/{assemblyLineId}", method = RequestMethod.GET)
    public ResponseEntity<AssemblyLineEntity> getAssemblyLineById(
            @PathVariable("assemblyLineId") @NotNull(message = "请输入流水线ID") Integer assemblyLineId
    ) {
        return ResponseEntity.ok(assemblyLineService.findById(assemblyLineId));
    }


    /**
     * 修改流水线
     * @param assemblyLineId 流水线ID
     * @param assemblyLineSaveField 流水线修改入参对象
     */
    @RequestMapping(value = "/assembly_line/{assemblyLineId}", method = RequestMethod.PUT)
    public ResponseEntity<AssemblyLineEntity> updateAssemblyLine(
            @PathVariable("assemblyLineId") @NotNull(message = "请输入流水线ID") Integer assemblyLineId,
            @RequestBody @Validated AssemblyLineSaveField assemblyLineSaveField
    ) {
        return ResponseEntity.ok(assemblyLineService.update(assemblyLineId, assemblyLineSaveField));
    }

    /**
     * 启动一个流水线任务
     */
    @RequestMapping(value = "/assembly_line/{assemblyLineId}/start", method = RequestMethod.POST)
    public ResponseEntity<Integer> updateAssemblyLine(
            CurrentUser currentUser,
            @PathVariable("assemblyLineId") @NotNull(message = "请输入流水线ID") Integer assemblyLineId,
            @NotBlank(message = "请指定分支") String branch,
            String commitId
    ) {
        AssemblyLineEntity assemblyLineEntity = assemblyLineService.findById(assemblyLineId).get();

        //配置转对象
        List<AssemblyLineStepTask> assemblyLineStepTasks = JSONArray.parseArray(assemblyLineEntity.getConfig(), AssemblyLineStepTask.class);

        //检查任务是否正常
        AssemblyLineLogEntity assemblyLineLogEntity = assemblyLineLogService.saveWithAssemblyLine(assemblyLineEntity, branch, commitId, currentUser.getUserId());

        //后端线程池执行 流水线任务
        assemblyLineTaskManager.execute(assemblyLineLogEntity.getId());

        return ResponseEntity.ok("流水线任务已提交", assemblyLineLogEntity.getId());
    }

    @RequestMapping(value = "/assembly_line_log/{assemblyLineLogId}", method = RequestMethod.GET)
    public ResponseEntity<AssemblyLineLogEntity> assemblyLineTaskLog(@PathVariable("assemblyLineLogId") @NotNull(message = "请输入流水线日志") Integer assemblyLineLogId) {
        //先查一下 运行时缓存中 有没有
        AssemblyLineLogEntity assemblyLineLogEntity = RunningCacheManager.getAssemblyLineLogEntity(assemblyLineLogId);
        if (assemblyLineLogEntity != null) {
            assemblyLineLogEntity.setStatus(AssemblyLineLogEntity.Status.RUNNING);
            return ResponseEntity.ok(assemblyLineLogEntity);
        } else {
            return ResponseEntity.ok(assemblyLineLogService.findById(assemblyLineLogId));
        }
    }

    @RequestMapping(value = "/assembly_line_log/{assemblyLineLogId}/task_logs", method = RequestMethod.GET)
    public ResponseEntity<List<AssemblyLineTaskLogEntity>> assemblyLineTaskLogList(@PathVariable("assemblyLineLogId") @NotNull(message = "请输入流水线日志") Integer assemblyLineLogId) {
        List<AssemblyLineTaskLogEntity> list = assemblyLineTaskLogService.findAllByAssemblyLineLogIdOrderById(assemblyLineLogId);
        AssemblyLineTaskLogEntity item;
        AssemblyLineTaskLogEntity cache;
        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            cache = RunningCacheManager.getAssemblyLineTaskLogEntity(item.getId());
            if (cache != null) {
                cache.setStatus(AssemblyLineLogEntity.Status.RUNNING);
                list.set(i, cache);
            }
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 获取运行时最新消息
     * TODO 暂时用定时器拉取  以后改成 websocket
     * @return httpCode==204 key不存在 或者 key对应的内容已经处理完毕
     */
    @RequestMapping(value = "/assembly_line/running", method = RequestMethod.POST)
    public ResponseEntity<String[]> unreadMsg(@RequestBody NewestLogRequest[] newestLogRequests) {
        String[] result = new String[newestLogRequests.length];
        for (int i = 0; i < newestLogRequests.length; i++) {
            String unread = RunningCacheManager.getUnread(newestLogRequests[i].getKey(), newestLogRequests[i].getRead());
            if (unread != null) {
                result[i] = unread;
            }
        }
        return ResponseEntity.ok(result);
    }

}
