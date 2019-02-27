package com.automate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 22:36
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected PageRequest buildPageRequest(Integer pageNo, Integer pageSize, Sort sort){
        if(pageNo == null || pageNo <= 0){
            pageNo = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 20;
        }
        if(sort == null){
            sort = Sort.unsorted();
        }
        // 这里 page参数是从 1开始的
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }
}
