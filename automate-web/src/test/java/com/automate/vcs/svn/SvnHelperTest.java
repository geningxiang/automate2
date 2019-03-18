package com.automate.vcs.svn;

import com.automate.vcs.IVCSRepository;
import com.automate.vcs.git.GitHelper;
import com.automate.vcs.vo.CommitLog;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author: genx
 * @date: 2019/3/18 17:16
 */
public class SvnHelperTest {

    @Test
    public void init() throws Exception {
        SvnHelper svnHelper = new SvnHelper(new IVCSRepository() {
            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public String getLocalDir() {
                return "E:\\work\\CaimaoCommon";
            }

            @Override
            public String getRemoteUrl() {
                return "https://www.kxtan.com/svn/projects/components/common/trunk";
            }

            @Override
            public String getUserName() {
                return "";
            }

            @Override
            public String getPassWord() {
                return "";
            }
        });

        svnHelper.init();
    }
}