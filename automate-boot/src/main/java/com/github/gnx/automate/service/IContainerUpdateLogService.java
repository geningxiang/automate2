package com.github.gnx.automate.service;

import com.github.gnx.automate.entity.ContainerUpdateLogEntity;
import org.springframework.data.domain.Sort;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/1 20:41
 */
public interface IContainerUpdateLogService {
    Iterable<ContainerUpdateLogEntity> findAll(Sort sort);
}
