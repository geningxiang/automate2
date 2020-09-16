package com.automate.service;

import com.automate.entity.AssemblyLineEntity;
import com.automate.task.background.BackgroundTaskManager;
import com.automate.task.background.build.BackgroundBuildTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/4 16:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/*.xml"})
public class AssemblyLineServiceTest {

    @Autowired
    private AssemblyLineService assemblyLineService;

    @Autowired
    private BackgroundTaskManager backgroundTaskManager;

    @Test
    public void findAll() {

    }

    @Test
    public void getModel() {
        Optional<AssemblyLineEntity> model = assemblyLineService.getModel(1);
        if (model.isPresent()) {
            System.out.println(model.get().getAutoTrigger());
        }
    }

    @Test
    public void save() {
        AssemblyLineEntity model = new AssemblyLineEntity();

        model.setProjectId(1);
        model.setBranches("*");
        model.setName("自动编译");
        model.setRemark("");
        model.setConfig("");

        model.setAdminId(1);
        model.setAutoTrigger(false);
        model.setTriggerCron("0 0 22 * * ? ");
        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
        model.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        assemblyLineService.save(model);
    }

    @Test
    public void test() throws Exception {
        Optional<AssemblyLineEntity> assemblyLineEntity = assemblyLineService.getModel(1);

        backgroundTaskManager.execute(new BackgroundBuildTask(assemblyLineEntity.get(), "master", null));

    }

    public static void main(String[] args) throws Exception {


//        JSONArray config = new JSONArray();
//
//        MavenAssemblyStepTask mavenTask = new MavenAssemblyStepTask();
//        mavenTask.setCustom("install");
//        JSONObject json = (JSONObject) JSONObject.toJSON(mavenTask);
//        json.put("className", mavenTask.getClass().getName());
//        config.add(json);
//        System.out.println(config.toJSONString());
//
//
//        MavenAssemblyStepTask mavenTask2 = new MavenAssemblyStepTask();
//        //mavenTask2.setCustom("compile");
//        JSONObject json2 = (JSONObject) JSONObject.toJSON(mavenTask2);
//        json2.put("className", mavenTask2.getClass().getName());
//        config.add(json2);
//
//        ExecStreamPrintMonitor execStreamPrintMonitor = new ExecStreamPrintMonitor();
//
//        for (int i = 0; i < config.size(); i++) {
//            JSONObject item = config.getJSONObject(i);
//
//            String className = item.getString("className");
//            if (StringUtils.isNotEmpty(className)) {
//                System.out.println(className);
//                Class cls = Class.forName(className);
//
//                if (IAssemblyStepTask.class.isAssignableFrom(cls)) {
//
//                    IAssemblyStepTask task = (IAssemblyStepTask) JSONObject.parseObject(item.toJSONString(), cls);
//
//                    ((MavenAssemblyStepTask) task).setExecStreamMonitor(execStreamPrintMonitor);
//                    System.out.println(JSON.toJSONString(task));
//
////                    task.invoke();
//                }
//            }
//        }
    }
}