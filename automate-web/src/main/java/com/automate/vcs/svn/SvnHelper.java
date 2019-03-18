package com.automate.vcs.svn;

import com.automate.vcs.AbstractVCSHelper;
import com.automate.vcs.IVCSRepository;
import com.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author: genx
 * @date: 2019/3/18 16:53
 */
public class SvnHelper extends AbstractVCSHelper {

    private ISVNAuthenticationManager authManager = null;

    public SvnHelper(IVCSRepository repository) {
        super(repository);
        if (StringUtils.isNotEmpty(this.userName) && StringUtils.isNotEmpty(this.passWord)) {
            authManager = SVNWCUtil.createDefaultAuthenticationManager(null, this.userName, this.passWord.toCharArray());
        }
    }

    @Override
    public List<String> init() throws Exception {
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(this.remoteUrl));

        repository.setAuthenticationManager(authManager);

        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager svnClientManager = SVNClientManager.newInstance(options, authManager);

        SVNUpdateClient updateClient = svnClientManager.getUpdateClient();


        long result = updateClient.doCheckout(SVNURL.parseURIEncoded(this.remoteUrl), new File(this.localDir), SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);

        System.out.println(result);
        repository.closeSession();
        return null;
    }

    @Override
    public List<String> update() throws Exception {



        return null;
    }

    @Override
    public List<String> update(String branchName) throws Exception {
        return null;
    }

    @Override
    public List<CommitLog> commitLogs(String branchName) throws Exception {
        return null;
    }

    @Override
    public String checkOut(String branchName, String commitId) throws Exception {
        return null;
    }

    @Override
    public void testConnetction() throws Exception {

    }

    @Override
    public boolean isLocalRepositoryExist() {
        return false;
    }
}
