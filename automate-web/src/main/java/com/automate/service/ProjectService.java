package com.automate.service;

import com.automate.dao.ProjectDAO;
import com.automate.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/24 23:26
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private EntityManager entityManager;

    public Iterable<ProjectEntity> getList() {
        return projectDAO.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<ProjectEntity> getModel(int id) {
        return projectDAO.findById(id);
    }

    /**
     * 添加对象
     **/
    public void save(ProjectEntity model) {
        projectDAO.save(model);
    }

    /**
     * 更新对象
     **/
    public void update(ProjectEntity model) {
        projectDAO.save(model);
    }

    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        projectDAO.deleteById(id);
    }
}
