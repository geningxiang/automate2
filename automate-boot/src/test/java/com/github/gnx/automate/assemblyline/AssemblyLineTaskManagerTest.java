package com.github.gnx.automate.assemblyline;

import com.github.gnx.automate.entity.AssemblyLineEntity;
import com.github.gnx.automate.entity.AssemblyLineLogEntity;
import com.github.gnx.automate.service.IAssemblyLineLogService;
import com.github.gnx.automate.service.IAssemblyLineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/30 22:20
 */
@SpringBootTest
public class AssemblyLineTaskManagerTest {

    @Autowired
    private IAssemblyLineService assemblyLineService;

    @Autowired
    private IAssemblyLineLogService assemblyLineLogService;

    @Autowired
    private AssemblyLineTaskManager assemblyLineTaskManager;


    @Test
    public void test() throws InterruptedException {

        AssemblyLineEntity assemblyLineEntity = assemblyLineService.findById(7).get();

        String branch = "develop";

        String commitId = "";

        int userId = 0;

        AssemblyLineLogEntity assemblyLineLogEntity = assemblyLineLogService.saveWithAssemblyLine(assemblyLineEntity, branch, commitId, userId);

        assemblyLineTaskManager.execute(assemblyLineLogEntity.getId());

        Thread.sleep(300000);
    }

}