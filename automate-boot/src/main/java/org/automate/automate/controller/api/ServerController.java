package org.automate.automate.controller.api;

import org.automate.automate.common.CurrentUser;
import org.automate.automate.common.ResponseEntity;
import org.automate.automate.entity.ApplicationEntity;
import org.automate.automate.entity.ServerEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/17 23:35
 */
@RestController
@RequestMapping("/api/v1")
public class ServerController {

    /**
     * 服务器资源列表
     * @return
     */
    @RequestMapping(value = "/servers", method = RequestMethod.GET)
    public ResponseEntity<List<ServerEntity>> serverList(CurrentUser currentUser) {
        return null;
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
     * 单个服务器下的应用列表
     * @return
     */
    @RequestMapping(value = "/server/{serverId}/applications", method = RequestMethod.GET)
    public ResponseEntity<List<ApplicationEntity>> applicationList(@PathVariable("serverId") Integer serverId, CurrentUser currentUser) {
        return null;
    }

    /**
     * 创建应用
     * @return
     */
    @RequestMapping(value = "/server/{serverId}/application", method = RequestMethod.POST)
    public ResponseEntity<ApplicationEntity> createApplication(@PathVariable("serverId") Integer serverId, CurrentUser currentUser, ApplicationEntity applicationEntity) {
        return null;
    }


}
