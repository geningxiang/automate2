package com.automate.controller.admin;

import com.automate.common.ResponseEntity;
import com.automate.entity.ApplicationUpdateLogEntity;
import com.automate.service.ApplicationUpdateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/27 21:51
 */
@Controller
@RequestMapping("/admin/application")
public class AdminApplicationController {

    @Autowired
    private ApplicationUpdateLogService applicationUpdateLogService;

    @RequestMapping("/list")
    public String list() {
        return "application/application_list";
    }

    @RequestMapping("/updateApplyList")
    public String updateApplyList() {
        return "application/application_update_apply_list";
    }

    @RequestMapping("/updateLogList")
    public String updateLogList() {
        return "application/application_update_log_list";
    }


    @RequestMapping("/updateLogDetail")
    public String updateLogDetail(Integer applicationUpdateLogId, ModelMap modelMap) {
        ApplicationUpdateLogEntity applicationUpdateLogEntity = null;
        if(applicationUpdateLogId != null && applicationUpdateLogId > 0 ){
            Optional<ApplicationUpdateLogEntity> model = applicationUpdateLogService.findById(applicationUpdateLogId);
            if (model.isPresent()) {
                applicationUpdateLogEntity = model.get();
            }
        }
        if(applicationUpdateLogEntity == null){
            applicationUpdateLogEntity = new ApplicationUpdateLogEntity();
        }
        modelMap.put("model", applicationUpdateLogEntity);
        return "application/application_update_log_detail";
    }

}
