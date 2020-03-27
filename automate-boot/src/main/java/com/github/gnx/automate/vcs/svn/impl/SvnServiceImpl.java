package com.github.gnx.automate.vcs.svn.impl;

import com.alibaba.fastjson.JSON;
import com.github.gnx.automate.contants.VcsType;
import com.github.gnx.automate.vcs.IVcsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/27 0:20
 */
@Service
public class SvnServiceImpl implements IVcsService {
    @Override
    public VcsType getVcsType() {
        return VcsType.SVN;
    }

    @Override
    public void test(String url, String name, String pwd) throws Exception {
        SVNClientManager svnClientManager = null;
        try {

            DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
            ISVNAuthenticationManager authManager = null;
            if (StringUtils.isNoneBlank(name, pwd)) {
                authManager = SVNWCUtil.createDefaultAuthenticationManager(null, name, pwd.toCharArray());

            }
            svnClientManager = SVNClientManager.newInstance(options, authManager);


            SVNLogClient svnLogClient = svnClientManager.getLogClient();
            svnLogClient.doLog(SVNURL.parseURIEncoded(url), new String[]{}, SVNRevision.HEAD, SVNRevision.HEAD, SVNRevision.create(0), false, false, false, 1, null,
                    svnLogEntry -> {

                    });
        } finally {
            if (svnClientManager != null) {
                svnClientManager.dispose();
            }
        }
    }
}
