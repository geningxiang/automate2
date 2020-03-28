//package com.github.gnx.automate.cache.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.github.gnx.automate.cache.ICacheHelper;
//import com.github.gnx.automate.cache.IVcsCacheHelper;
//import com.github.gnx.automate.entity.ProjectEntity;
//import com.github.gnx.automate.service.IProjectService;
//import com.github.gnx.automate.vcs.VcsHelper;
//import com.github.gnx.automate.vcs.vo.VcsBranch;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// * @author genx
// * @date 2020/3/28 17:04
// */
//@Component
//public class VcsCacheHelper implements IVcsCacheHelper {
//
//    private final String BRANCH_LIST_CACHE_KEY = "project_branch_hash_";
//
//    @Autowired
//    private ICacheHelper cacheHelper;
//
//    @Autowired
//    private IProjectService projectService;
//
//    @Autowired
//    private VcsHelper vcsHelper;
//
//    @Override
//    public List<VcsBranch> getBranchList(int projectId) throws Exception {
//        Map<String, String> map = cacheHelper.hashEntries(BRANCH_LIST_CACHE_KEY + projectId);
//        if (map != null && map.size() > 0) {
//            List<VcsBranch> list = new ArrayList();
//            for (String value : map.values()) {
//                list.add(JSON.parseObject(value, VcsBranch.class));
//            }
//            return list;
//        }
//
//        Optional<ProjectEntity> project = projectService.getModel(projectId);
//        if (project == null) {
//            return null;
//        }
//        List<VcsBranch> list = vcsHelper.update(project.get());
//        if (list.size() > 0) {
//            map = new HashMap(16);
//            for (VcsBranch branch : list) {
//                map.put(branch.getBranchName(), JSON.toJSONString(branch));
//            }
//            cacheHelper.hashPutAll(BRANCH_LIST_CACHE_KEY + projectId, map);
//        }
//        return list;
//
//    }
//}
