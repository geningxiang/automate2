package com.github.gnx.automate.vcs;

import com.github.gnx.automate.common.SystemUtil;
import com.github.gnx.automate.contants.VcsType;
import com.github.gnx.automate.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 21:44
 */
@Component
public class VcsHelper {

    @Autowired
    private IVcsService[] vcsServices;

    private IVcsService getVcsService(int vcsType) {
        for (IVcsService o : vcsServices) {
            if (o.getVcsType() != null && o.getVcsType().ordinal() == vcsType) {
                return o;
            }
        }
        throw new IllegalArgumentException("未知的版本控制类型:" + vcsType);
    }

    public void test(VcsType vcsType, String url, String name, String pwd) throws Exception {
        getVcsService(vcsType.ordinal()).test(url, new VcsUserNamePwdCredentialsProvider(name, pwd));
    }

    public int update(ProjectEntity projectEntity) throws Exception {
        return getVcsService(projectEntity.getVcsType()).update(
                projectEntity.getId(),
                projectEntity.getVcsUrl(),
                SystemUtil.getProjectSourceCodeDir(projectEntity),
                new VcsUserNamePwdCredentialsProvider(projectEntity.getVcsUserName(), projectEntity.getVcsPassWord()));
    }


    public String checkOut(ProjectEntity projectEntity, String branch, String commitId) throws Exception {
        return getVcsService(projectEntity.getVcsType()).checkOut(
                projectEntity.getId(),
                projectEntity.getVcsUrl(),
                SystemUtil.getProjectSourceCodeDir(projectEntity),
                new VcsUserNamePwdCredentialsProvider(projectEntity.getVcsUserName(), projectEntity.getVcsPassWord()),
                branch,
                commitId
        );
    }

}
