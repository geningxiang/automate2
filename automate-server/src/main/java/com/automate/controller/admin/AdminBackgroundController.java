package com.automate.controller.admin;

import com.automate.task.background.AbstractBackgroundTask;
import com.automate.task.background.BackgroundLock;
import com.automate.task.background.BackgroundTaskManager;
import com.automate.task.background.IBackgroundMonitor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/3 8:32
 */
@Controller
@RequestMapping("/admin/background")
public class AdminBackgroundController {

    @Autowired
    private BackgroundTaskManager backgroundTaskManager;


    @RequestMapping("/dashboard")
    public String dashboard(ModelMap modelMap) {
        modelMap.put("runningList", backgroundTaskManager.runningList());

        modelMap.put("waitingList", backgroundTaskManager.waitingList());

        return "background/dashboard";
    }


}
