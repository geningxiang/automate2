package com.automate.vcs.svn;

import com.automate.vcs.AbstractVCSHelper;
import com.automate.vcs.IVCSRepository;
import com.automate.vcs.vo.CommitLog;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/18 16:53
 */
public class SvnHelper extends AbstractVCSHelper {
    private Logger logger = LoggerFactory.getLogger(getClass());


    public SvnHelper(IVCSRepository repository) {
        super(repository);

    }

    @Override
    public List<String> init() throws Exception {
        return this.update();
    }

    @Override
    public List<String> update() throws Exception {


        SVNClientManager svnClientManager = getSVNClientManager();
        long revision;
        boolean isCreate = false;
        if (isLocalRepositoryExist()) {
            SVNUpdateClient updateClient = svnClientManager.getUpdateClient();
            revision = updateClient.doUpdate(new File(this.localDir), SVNRevision.HEAD, SVNDepth.INFINITY, true, true);
        } else {
            SVNUpdateClient updateClient = svnClientManager.getUpdateClient();
            revision = updateClient.doCheckout(SVNURL.parseURIEncoded(this.remoteUrl), new File(this.localDir), SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
            isCreate = true;
        }

        logger.info("svn " + (isCreate ? "clone" : "update") + " revision:" + revision);

        svnClientManager.dispose();
        return Lists.newArrayList("");
    }

    @Override
    public List<String> update(String branchName) throws Exception {
        return update();
    }

    /**
     * 提交记录
     *
     * @param branchName 分支名  svn 没有分支的概念
     * @return
     * @throws Exception
     */
    @Override
    public List<CommitLog> commitLogs(String branchName) throws Exception {
        SVNClientManager svnClientManager = getSVNClientManager();
        SVNLogClient svnLogClient = svnClientManager.getLogClient();
        final List<CommitLog> list = new ArrayList(SvnContants.LOG_MAX_READ_COUNT);
        svnLogClient.doLog(SVNURL.parseURIEncoded(remoteUrl), new String[]{}, SVNRevision.HEAD, SVNRevision.HEAD, SVNRevision.create(0), false, false, false, SvnContants.LOG_MAX_READ_COUNT, null,
                svnLogEntry -> list.add(SvnFormat.parse(svnLogEntry)));
        svnClientManager.dispose();
        return list;
    }

    @Override
    public String checkOut(String branchName, String commitId) throws Exception {
        SVNClientManager svnClientManager = null;
        try {
            svnClientManager = getSVNClientManager();
            if (isLocalRepositoryExist()) {
                long result;
                SVNUpdateClient updateClient = svnClientManager.getUpdateClient();
                if (StringUtils.isNotBlank(commitId)) {
                    long revision = NumberUtils.toLong(commitId);
                    if (revision <= 0) {
                        throw new IllegalArgumentException("commitId must be long");
                    }
                    result = updateClient.doCheckout(SVNURL.parseURIEncoded(this.remoteUrl), new File(this.localDir), SVNRevision.HEAD, SVNRevision.create(revision), SVNDepth.INFINITY, true);
                } else {
                    result = updateClient.doCheckout(SVNURL.parseURIEncoded(this.remoteUrl), new File(this.localDir), SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
                }
                logger.info("svn checkout revision:" + result);
            } else {
                throw new IllegalArgumentException("请先初始化仓库");
            }
        } finally {
            if (svnClientManager != null) {
                svnClientManager.dispose();
            }
        }


        return "";
    }

    @Override
    public void testConnetction() throws Exception {
        SVNClientManager svnClientManager = getSVNClientManager();
        SVNLogClient svnLogClient = svnClientManager.getLogClient();
        svnLogClient.doList(SVNURL.parseURIEncoded(remoteUrl), SVNRevision.HEAD, SVNRevision.HEAD, true, SVNDepth.IMMEDIATES, -1, svnDirEntry -> {
        });
        svnClientManager.dispose();
    }

    @Override
    public boolean isLocalRepositoryExist() {
        File file = new File(this.localDir);
        if (!file.exists()) {
            return false;
        }
        if (!this.localDir.endsWith(SvnContants.DOT_SVN)) {
            file = new File(this.localDir + File.separator + SvnContants.DOT_SVN);
        }
        if (!file.exists()) {
            return false;
        }
        return true;

    }

    @Deprecated
    private SVNRepository getSVNRepository() throws SVNException {
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(this.remoteUrl));
        if (StringUtils.isNotEmpty(this.userName) && StringUtils.isNotEmpty(this.passWord)) {
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(null, this.userName, this.passWord.toCharArray());
            repository.setAuthenticationManager(authManager);
        }
        return repository;
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
