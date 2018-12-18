package com.automate.service;

import com.automate.dao.ServerDAO;
import com.automate.entity.ServerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/17 23:49
 */
@Service
public class ServerService {

    @Autowired
    private ServerDAO serverDAO;

    public List<ServerEntity> getList(){
        String hql = "from ServerEntity order by id";
        return serverDAO.getList(hql);
    }

    /**
     * 查询对象
     **/
    public ServerEntity getModel(int id) {
        return serverDAO.getModel(id);
    }

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

}
