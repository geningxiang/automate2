//package com.automate.service;
//
//import com.automate.entity.SourceCodeEntity;
//import com.automate.vcs.git.GitHelper;
//import com.automate.vcs.git.hook.GithubHook;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @author: genx
// * @date: 2019/1/24 23:28
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:/spring/*.xml"})
//public class SourceCodeServiceTest {
//
//    @Autowired
//    private SourceCodeService sourceCodeService;
//
//    @Test
//    public void getList() {
//    }
//
//    @Test
//    public void save1() {
//        SourceCodeEntity model = new SourceCodeEntity();
//        model.setType(SourceCodeEntity.Type.JAVA);
//        model.setName("SpringBootDemo");
//
//        model.setRemark("测试用");
//        model.setVcsType(SourceCodeEntity.VcsType.GIT);
//        model.setVcsUrl("http://60.190.13.162:6104/genx/SpringBootDemo.git");
//        model.setUserName("genx");
//        model.setPassWord("ge10111011");
//        model.setCompileType(SourceCodeEntity.CompileType.MAVEN);
//        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        model.setStatus(SourceCodeEntity.Status.ACTIVATE);
//
//        sourceCodeService.save(model);
//    }
//
//    @Test
//    public void save2() {
//        SourceCodeEntity model = new SourceCodeEntity();
//        model.setType(SourceCodeEntity.Type.JAVA);
//        model.setName("InformationDemo");
//        model.setRemark("资讯教学");
//        model.setVcsType(SourceCodeEntity.VcsType.GIT);
//        model.setVcsUrl("https://github.com/geningxiang/InformationDemo.git");
//        model.setUserName("geningxiang");
//        model.setPassWord("ge10111011");
//        model.setCompileType(SourceCodeEntity.CompileType.MAVEN);
//        model.setCreateTime(new Timestamp(System.currentTimeMillis()));
//        model.setStatus(SourceCodeEntity.Status.ACTIVATE);
//
//        sourceCodeService.save(model);
//    }
//
//
//    @Test
//    public void init() throws Exception {
//        Optional<SourceCodeEntity> sourceCodeEntity = sourceCodeService.getModel(1);
//        sourceCodeService.init(sourceCodeEntity.get());
//    }
//
//    @Test
//    public void sync() throws Exception {
//        Optional<SourceCodeEntity> sourceCodeEntity = sourceCodeService.getModel(1);
//        sourceCodeService.sync(sourceCodeEntity.get());
//    }
//
//    @Test
//    public void syncWidthBranchList() throws Exception {
//        Optional<SourceCodeEntity> sourceCodeEntity = sourceCodeService.getModel(1);
//        GitHelper gitHelper = new GitHelper(sourceCodeEntity.get());
//        List<String> branchList = new ArrayList<>();
//        branchList.add("master");
//        branchList.add("develop1");
//        branchList.add("develop2");
//        sourceCodeService.sync(sourceCodeEntity.get(), gitHelper, branchList);
//    }
//}