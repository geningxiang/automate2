//package com.automate.task.background.assembly.impl;
//
//import com.automate.common.SystemConfig;
//import com.automate.common.utils.SpringContextUtil;
//import com.automate.entity.ProjectPackageEntity;
//import com.automate.service.ProjectPackageService;
//import com.automate.task.background.assembly.AbstractAssemblyStepTask;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// * 安装包提取
// *
// * @author: genx
// * @date: 2019/3/5 20:57
// */
//public class PackageExtractAssemblyStepTask extends AbstractAssemblyStepTask {
//
//    private final String BASE_DIR = "${baseDir}";
//
//    private String path;
//
//    public String getPath() {
//        if (this.path.contains(BASE_DIR)) {
//            return this.path.replace(BASE_DIR, SystemConfig.getProjectBaseDir(getProjectEntity()));
//        } else {
//            return this.path;
//        }
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    @Override
//    public void valid() throws Exception {
//
//    }
//
//    @Override
//    public boolean doInvoke() throws Exception {
//        File file = new File(getPath());
//
//        if (!file.exists()) {
//            throw new IOException("文件不存在:" + file.getAbsolutePath());
//        }
//
//        ProjectPackageService projectPackageService = SpringContextUtil.getBean("projectPackageService", ProjectPackageService.class);
//
//        //TODO 提取文件包
//        logger.debug("开始提取文件包: {}", file.getAbsolutePath());
//        appendLine("开始提取文件包:" + file.getAbsolutePath());
//
//        ProjectPackageEntity applicationPackageEntity = projectPackageService.create(assemblyLineLogEntity.getSourceCodeId(), "", assemblyLineLogEntity.getBranch(), assemblyLineLogEntity.getCommitId(), "后台构建生成", file, ProjectPackageEntity.Type.WHOLE, 0);
//        appendLine("copy file to " + applicationPackageEntity.getFilePath());
//        return true;
//
//    }
//
//}
