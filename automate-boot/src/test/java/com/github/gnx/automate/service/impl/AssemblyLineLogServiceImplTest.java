package com.github.gnx.automate.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.service.IAssemblyLineLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/29 0:00
 */
//启动Spring
@SpringBootTest
public class AssemblyLineLogServiceImplTest {

    @Autowired
    private IAssemblyLineLogService assemblyLineLogService;

    @Test
    public void test() {
        int projectId = 1;

        Sort sort = Sort.by(Sort.Direction.DESC, "id"); //创建时间降序排序


        Pageable pageable = PageRequest.of(1, 20, sort);

        Page<AssemblyLineLogEntity> page = assemblyLineLogService.queryPageByProjectId(projectId, pageable);

        System.out.println(page.getTotalElements());

        System.out.println(JSON.toJSONString(page));

    }
}