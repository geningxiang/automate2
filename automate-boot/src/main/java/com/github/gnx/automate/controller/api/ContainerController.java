package com.github.gnx.automate.controller.api;

import com.github.gnx.automate.common.CurrentUser;
import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.service.IContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/7/27 20:12
 */
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ContainerController {

    @Autowired
    private IContainerService containerService;


    /**
     * 服务器资源列表
     * @return
     */
    @RequestMapping(value = "/containers", method = RequestMethod.GET)
    public ResponseEntity<Iterable<ContainerEntity>> serverList(CurrentUser currentUser) {
        Iterable<ContainerEntity> list = this.containerService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * 检查容器状态
     * @param currentUser
     * @param containerId
     * @return
     */
    @RequestMapping(value = "/container/{containerId}/check", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(CurrentUser currentUser, @PathVariable("containerId") @NotNull(message = "请输入容器ID") Integer containerId) throws Exception {
        return ResponseEntity.ok(containerService.check(containerId));
    }

    @RequestMapping(value = "/container/{containerId}/start", method = RequestMethod.POST)
    public ResponseEntity start(CurrentUser currentUser, @PathVariable("containerId") @NotNull(message = "请输入容器ID") Integer containerId) throws Exception {
        containerService.start(containerId);
        return ResponseEntity.ok();
    }


    @RequestMapping(value = "/container/{containerId}/stop", method = RequestMethod.POST)
    public ResponseEntity stop(CurrentUser currentUser, @PathVariable("containerId") @NotNull(message = "请输入容器ID") Integer containerId) throws Exception {
        containerService.stop(containerId);
        return ResponseEntity.ok();
    }
}
