package com.github.gnx.automate.vcs;

import com.github.gnx.automate.contants.VcsType;
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

    private IVcsService getVcsService(VcsType vcsType) {
        for (IVcsService o : vcsServices) {
            if (o.getVcsType() != null && vcsType != null && o.getVcsType() == vcsType) {
                return o;
            }
        }
        throw new IllegalArgumentException("未知的版本控制类型:" + vcsType);
    }

    public void test(VcsType vcsType, String url, String name, String pwd) throws Exception{
        getVcsService(vcsType).test(url, name, pwd);
    }

}
