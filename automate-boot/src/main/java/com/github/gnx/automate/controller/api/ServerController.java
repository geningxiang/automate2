package com.github.gnx.automate.controller.api;

import com.github.gnx.automate.entity.ContainerEntity;
import com.github.gnx.automate.entity.ServerEntity;
import com.github.gnx.automate.common.CurrentUser;
import com.github.gnx.automate.common.ResponseEntity;
import com.github.gnx.automate.service.IServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 23:35
 */
@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ServerController {

    @Autowired
    private IServerService serverService;

    /**
     * 服务器资源列表
     * @return
     */
    @RequestMapping(value = "/servers", method = RequestMethod.GET)
    public ResponseEntity<Iterable<ServerEntity>> serverList(CurrentUser currentUser) {
        Iterable<ServerEntity> list = this.serverService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * 添加服务器资源
     * @param serverEntity
     * @return
     */
    @RequestMapping(value = "/server", method = RequestMethod.POST)
    public ResponseEntity<ServerEntity> create(CurrentUser currentUser, ServerEntity serverEntity) {
        return null;
    }

    /**
     * 修改服务器资源
     * @return
     */
    @RequestMapping(value = "/server/{serverId}", method = RequestMethod.PATCH)
    public ResponseEntity<ServerEntity> edit(CurrentUser currentUser, @PathVariable("serverId") Integer serverId, ServerEntity serverEntity) {
        return null;
    }

    /**
     * 指定服务器下的容器列表
     * @return
     */
    @RequestMapping(value = "/server/{serverId}/containers", method = RequestMethod.GET)
    public ResponseEntity<List<ContainerEntity>> containerList(@PathVariable("serverId") Integer serverId, CurrentUser currentUser) {
        return null;
    }

    /**
     * 创建容器
     * @return
     */
    @RequestMapping(value = "/server/{serverId}/container", method = RequestMethod.POST)
    public ResponseEntity<ContainerEntity> createContainer(@PathVariable("serverId") Integer serverId, CurrentUser currentUser, ContainerEntity containerEntity) {
        return null;
    }


}
