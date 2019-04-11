package com.automate.service;

import com.automate.entity.ApplicationUpdateApplyEntity;
import com.automate.repository.ApplicationUpdateApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/11 22:35
 */
@Service
public class ApplicationUpdateApplyService {

    @Autowired
    private ApplicationUpdateApplyRepository applicationUpdateApplyRepository;

    public Page<ApplicationUpdateApplyEntity> findAll(Pageable pageable) {
        return applicationUpdateApplyRepository.findAll(pageable);
    }

    public void compare() {

    }

}
