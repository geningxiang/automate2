package com.automate.controller;

import com.alibaba.fastjson.JSONArray;
import com.automate.common.ResponseEntity;
import com.automate.common.ResponseStatus;
import com.automate.entity.ServerEntity;
import com.automate.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/18 23:11
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @RequestMapping("/list")
    public ResponseEntity<JSONArray> list(){
        List<ServerEntity> list = serverService.getList();
        JSONArray array = new JSONArray(list.size());
        for (ServerEntity serverEntity : list) {
            array.add(serverEntity.toJson());
        }
        return ResponseEntity.ok(array);
    }


    /**
     * 获取服务器的  ps aux 信息
     * @param id
     * @return
     */
    @RequestMapping("/psAux")
    public ResponseEntity<Object> psAux(Integer id){
        if(id == null || id <= 0){
            return ResponseEntity.of(ResponseStatus.BAD_REQUEST, "参数错误", null);
        }
        ServerEntity model = serverService.getModel(id);
        if(model == null){
            return ResponseEntity.of(ResponseStatus.NOT_FOUND, "未找到相应的服务器", null);
        }


        return null;
    }
}
