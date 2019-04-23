package com.automate.service;

import com.automate.entity.ProjectBranchEntity;
import com.automate.repository.ProjectBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
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
    private ProjectBranchRepository projectBranchRepository;

    @Autowired
    private EntityManager entityManager;

    public Iterable<ProjectBranchEntity> findAll() {
        //TODO 查询列表时  排除 comminLog 大字段
        return projectBranchRepository.findAll(Sort.by("lastCommitTime").descending());
    }

    public List<ProjectBranchEntity> getList(int sourceCodeId) {
        return projectBranchRepository.getAllByProjectIdOrderByLastCommitTime(sourceCodeId);
    }

    /**
     * 查询对象
     **/
    public Optional<ProjectBranchEntity> getModel(int id) {
        return projectBranchRepository.findById(id);
    }

    /**
     * 添加更新对象
     **/
    public void save(ProjectBranchEntity model) {
        projectBranchRepository.save(model);
    }

    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        projectBranchRepository.deleteById(id);
    }


}
