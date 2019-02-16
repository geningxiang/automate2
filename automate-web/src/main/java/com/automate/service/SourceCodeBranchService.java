package com.automate.service;

import com.alibaba.fastjson.JSON;
import com.automate.entity.SourceCodeBranchEntity;
import com.automate.repository.SourceCodeBranchRepository;
import com.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
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
public class SourceCodeBranchService {

    @Autowired
    private SourceCodeBranchRepository sourceCodeBranchRepository;

    @Autowired
    private EntityManager entityManager;

    public Iterable<SourceCodeBranchEntity> findAll() {
        //TODO 查询列表时  排除 comminLog 大字段
        return sourceCodeBranchRepository.findAll(Sort.by("lastCommitTime").descending());
    }

    public List<SourceCodeBranchEntity> getList(int sourceCodeId) {
        return sourceCodeBranchRepository.getAllBySourceCodeIdOrderByLastCommitTime(sourceCodeId);
    }

    /**
     * 查询对象
     **/
    public Optional<SourceCodeBranchEntity> getModel(int id) {
        return sourceCodeBranchRepository.findById(id);
    }

    /**
     * 添加更新对象
     **/
    public void save(SourceCodeBranchEntity model) {
        sourceCodeBranchRepository.save(model);
    }

    /**
     * 删除对象
     **/
    public void deleteById(int id) {
        sourceCodeBranchRepository.deleteById(id);
    }


}
