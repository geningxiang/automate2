package com.genx.auotmate.service;

import com.genx.auotmate.dao.ServerDAO;
import com.genx.auotmate.entity.ServerEntity;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/17 23:49
 */
@Service
public class ServerService {

    private ServerDAO serverDAO;

    /**
     * 添加对象
     **/
    public void save(ServerEntity model) {
        serverDAO.save(model);
    }

    /**
     * 更新对象
     **/
    public void update(ServerEntity model) {
        serverDAO.update(model);
    }

    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        serverDAO.deleteById(id);
    }

    public void setServerDAO(ServerDAO serverDAO) {
        this.serverDAO = serverDAO;
    }
}
