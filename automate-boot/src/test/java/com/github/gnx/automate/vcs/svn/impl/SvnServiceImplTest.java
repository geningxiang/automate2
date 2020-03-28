package com.github.gnx.automate.vcs.svn.impl;


import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.tmatesoft.svn.core.SVNException;
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
 * @date 2020/3/28 10:37
 */
public class SvnServiceImplTest {
    private String userName = "gnx";
    private String passWord = "gnx2014";

    @Test
    public void test() throws SVNException {
        SVNClientManager svnClientManager = getSVNClientManager();
        SVNLogClient svnLogClient = svnClientManager.getLogClient();

        svnLogClient.doLog(SVNURL.parseURIEncoded("https://www.kxtan.com/svn/projects/Libs"),
                new String[]{},
                SVNRevision.HEAD,
                SVNRevision.HEAD,
                SVNRevision.create(0),
                false,
                false,
                false,
                10,
                null,
                svnLogEntry -> {
                    System.out.println(svnLogEntry.getRevision() + " | " + svnLogEntry.getMessage());
                });

        System.out.println("#############");

        svnLogClient.doLog(SVNURL.parseURIEncoded("https://www.kxtan.com/svn/projects/Libs"),
                new String[]{},
                SVNRevision.HEAD,
                SVNRevision.create(17346),
                SVNRevision.create(0),
                false,
                false,
                false,
                10,
                null,
                svnLogEntry -> {
                    System.out.println(svnLogEntry.getRevision() + " | " + svnLogEntry.getMessage());
                });

        svnClientManager.dispose();

    }


    private SVNClientManager getSVNClientManager() throws SVNException {
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager authManager = null;
        if (StringUtils.isNotEmpty(this.userName) && StringUtils.isNotEmpty(this.passWord)) {
            authManager = SVNWCUtil.createDefaultAuthenticationManager(null, this.userName, this.passWord.toCharArray());

        }
        return SVNClientManager.newInstance(options, authManager);
    }
}