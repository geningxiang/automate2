package com.automate.service;

import com.automate.dao.ProjectBranchDAO;
import com.automate.entity.ProjectBranchEntity;
import com.automate.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/28 23:25
 */
@Service
public class ProjectBranchService {

    @Autowired
    private ProjectBranchDAO projectBranchDAO;

    @Autowired
    private EntityManager entityManager;

    public Iterable<ProjectBranchEntity> findAll() {
        return projectBranchDAO.findAll(Sort.by("id"));
    }

    /**
     * 查询对象
     **/
    public Optional<ProjectBranchEntity> getModel(int id) {
        return projectBranchDAO.findById(id);
    }

    /**
     * 添加更新对象
     **/
    public void save(ProjectBranchEntity model) {
        projectBranchDAO.save(model);
    }

    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        projectBranchDAO.deleteById(id);
    }
}
