package com.automate.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.automate.common.ResponseEntity;
import com.automate.common.SessionUserManager;
import com.automate.controller.BaseController;
import com.automate.entity.ApplicationUpdateApplyEntity;
import com.automate.entity.ApplicationUpdateLogEntity;
import com.automate.service.ApplicationUpdateApplyService;
import com.automate.service.ApplicationUpdateLogService;
import com.automate.task.background.BackgroundTaskManager;
import com.automate.task.background.update.BackgroundUpdateTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2019/8/12 21:12
 */
@RestController
@RequestMapping("/api")
public class ApplicationUpdateController extends BaseController {

    @Autowired
    private ApplicationUpdateApplyService applicationUpdateApplyService;

    @Autowired
    private ApplicationUpdateLogService applicationUpdateLogService;

    @Autowired
    private BackgroundTaskManager backgroundTaskManager;


    /**
     * 应用更新申请
     * @param packageId
     * @param applicationIds
     * @param request
     * @return
     */
    @RequestMapping(value = "/applicationUpdate/apply", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity apply(int packageId, int[] applicationIds, HttpServletRequest request) {
        try {
            applicationUpdateApplyService.apply(packageId, applicationIds, SessionUserManager.getSessionUser(request).getAdminUser().getId());
            return ResponseEntity.ok("已申请", null);
        } catch (Exception e) {
            return ResponseEntity.of(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 应用更新申请列表
     * @param projectId
     * @param applicationId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/applicationUpdate/applys", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity applyList(@Nullable Integer projectId, @Nullable Integer applicationId, Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<ApplicationUpdateApplyEntity> specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList(2);
            if (projectId != null && projectId > 0) {
                predicates.add(criteriaBuilder.equal(root.get("projectId"), projectId));
            }
            if (applicationId != null && applicationId > 0) {
                predicates.add(criteriaBuilder.equal(root.get("applicationId"), applicationId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<ApplicationUpdateApplyEntity> pager = applicationUpdateApplyService.findAll(specification, pageRequest);
        return ResponseEntity.ok(pager);
    }

    /**
     * 确认更新
     * @param applyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/applicationUpdate/confirm", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<JSONObject> confirm(int applyId, HttpServletRequest request) throws Exception {
        Optional<ApplicationUpdateApplyEntity> applicationUpdateApplyEntity = applicationUpdateApplyService.findById(applyId);
        if (applicationUpdateApplyEntity.isPresent()) {

            if(applicationUpdateApplyEntity.get().getStatus() != ApplicationUpdateApplyEntity.Status.APPLY.ordinal()){
                return ResponseEntity.of(HttpStatus.BAD_REQUEST, "当前申请已处理:status=" + applicationUpdateApplyEntity.get().getStatus());
            }
            //TODO 申请的状态

            applicationUpdateApplyEntity.get().setAuditUserId(SessionUserManager.getSessionUser(request).getAdminUser().getId());

            backgroundTaskManager.execute(new BackgroundUpdateTask(applicationUpdateApplyEntity.get()));
            return ResponseEntity.ok("开始执行,请关注后台任务", null);

        } else {
            return ResponseEntity.of(HttpStatus.NOT_FOUND, "未找到相应的更新申请,applyId:" + applyId);
        }
    }


    @RequestMapping(value = "/applicationUpdate/logs", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity logList(@Nullable Integer projectId, @Nullable Integer applicationId, Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<ApplicationUpdateLogEntity> specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList(2);
            if (projectId != null && projectId > 0) {
                predicates.add(criteriaBuilder.equal(root.get("projectId"), projectId));
            }
            if (applicationId != null && applicationId > 0) {
                predicates.add(criteriaBuilder.equal(root.get("applicationId"), applicationId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<ApplicationUpdateLogEntity> pager = applicationUpdateLogService.findAll(specification, pageRequest);
        return ResponseEntity.ok(pager);
    }

}
