package com.github.gnx.automate.vcs.svn.impl;

import com.github.gnx.automate.contants.VcsType;
import com.github.gnx.automate.event.IEventPublisher;
import com.github.gnx.automate.event.vo.BranchUpdatedEvent;
import com.github.gnx.automate.vcs.IVcsCredentialsProvider;
import com.github.gnx.automate.vcs.IVcsService;
import com.github.gnx.automate.vcs.VcsUserNamePwdCredentialsProvider;
import com.github.gnx.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/27 0:20
 */
@Service
public class SvnServiceImpl implements IVcsService {

    /**
     * 仓库文件夹后缀 .svn
     */
    public static final String DOT_SVN = ".svn";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IEventPublisher eventPublisher;

    @Override
    public VcsType getVcsType() {
        return VcsType.SVN;
    }

    @Override
    public void test(String url, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception {
        SVNClientManager svnClientManager = null;
        try {
            svnClientManager = getSVNClientManager(vcsCredentialsProvider);
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

    @Override
    public int clone(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception {
        return this.doUpdate(projectId, remoteUrl, localDir, vcsCredentialsProvider);
    }

    @Override
    public int doUpdate(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception {
        SVNClientManager svnClientManager = null;
        try {
            svnClientManager = getSVNClientManager(vcsCredentialsProvider);


            int updated = 0;

            SVNUpdateClient updateClient = svnClientManager.getUpdateClient();
            long revision;
            if (localDir.exists()) {
                long currentRevision = getCurrentRevision(remoteUrl, svnClientManager);
                revision = updateClient.doUpdate(localDir, SVNRevision.HEAD, SVNDepth.INFINITY, true, true);
                if (revision > currentRevision) {
                    updated++;
                }

            } else {
                revision = updateClient.doCheckout(SVNURL.parseURIEncoded(remoteUrl), localDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
                updated++;
            }

            // 暂时只认为是单个分支
            if (updated > 0) {

                List<CommitLog> commitLogList = new ArrayList(IVcsService.DEFAULT_LIMIT);
                SVNLogClient svnLogClient = svnClientManager.getLogClient();
                svnLogClient.doLog(SVNURL.parseURIEncoded(remoteUrl), new String[]{}, SVNRevision.HEAD, SVNRevision.HEAD, SVNRevision.create(0), false, false, false, IVcsService.DEFAULT_LIMIT, null,
                        svnLogEntry -> {
                            commitLogList.add(parse(svnLogEntry));
                        });
                eventPublisher.publishEvent(new BranchUpdatedEvent(projectId, "trunk", commitLogList));
            }

            return updated;
        } finally {
            if (svnClientManager != null) {
                svnClientManager.dispose();
            }
        }
    }


    private long getCurrentRevision(String remoteUrl, SVNClientManager svnClientManager) throws SVNException {
        SVNLogClient svnLogClient = svnClientManager.getLogClient();
        final long[] revisions = new long[1];
        svnLogClient.doLog(SVNURL.parseURIEncoded(remoteUrl), new String[]{}, SVNRevision.HEAD, SVNRevision.HEAD, SVNRevision.create(0), false, false, false, 1, null,
                svnLogEntry -> revisions[0] = svnLogEntry.getRevision());
        return revisions[0];
    }

    @Override
    public List<CommitLog> commitLog(String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider, String branch, String startId, int limit) throws Exception {
        SVNClientManager svnClientManager = null;
        try {
            svnClientManager = getSVNClientManager(vcsCredentialsProvider);
            SVNLogClient svnLogClient = svnClientManager.getLogClient();
            final List<CommitLog> list = new ArrayList(limit);

            SVNRevision startRevision = SVNRevision.HEAD;
            if (StringUtils.isNotBlank(startId)) {
                startRevision = SVNRevision.create(NumberUtils.toLong(startId));
            }

            svnLogClient.doLog(SVNURL.parseURIEncoded(remoteUrl), new String[]{}, SVNRevision.HEAD, startRevision, SVNRevision.create(0), false, false, false, limit, null,
                    svnLogEntry -> list.add(parse(svnLogEntry)));
            svnClientManager.dispose();
            return list;
        } finally {
            if (svnClientManager != null) {
                svnClientManager.dispose();
            }
        }
    }

    @Override
    public String checkOut(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider, String branch, String commitId) throws Exception {

        SVNClientManager svnClientManager = null;
        long result;
        try {
            svnClientManager = getSVNClientManager(vcsCredentialsProvider);

            if (localDir.exists()) {

                SVNUpdateClient updateClient = svnClientManager.getUpdateClient();
                if (StringUtils.isNotBlank(commitId)) {
                    long revision = NumberUtils.toLong(commitId);
                    if (revision <= 0) {
                        throw new IllegalArgumentException("commitId must be long");
                    }
                    result = updateClient.doCheckout(SVNURL.parseURIEncoded(remoteUrl), localDir, SVNRevision.HEAD, SVNRevision.create(revision), SVNDepth.INFINITY, true);
                } else {
                    result = updateClient.doCheckout(SVNURL.parseURIEncoded(remoteUrl), localDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
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
        return String.valueOf(result);
    }

    private CommitLog parse(SVNLogEntry svnLogEntry) {
        CommitLog commitLog = new CommitLog();
        commitLog.setId(String.valueOf(svnLogEntry.getRevision()));
        commitLog.setAuthor(svnLogEntry.getAuthor());
        commitLog.setCommitTime(svnLogEntry.getDate().getTime());
        commitLog.setMsg(svnLogEntry.getMessage());
        return commitLog;
    }

    private SVNClientManager getSVNClientManager(IVcsCredentialsProvider vcsCredentialsProvider) {
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        ISVNAuthenticationManager authManager = null;
        if (vcsCredentialsProvider != null && vcsCredentialsProvider instanceof VcsUserNamePwdCredentialsProvider) {
            if (((VcsUserNamePwdCredentialsProvider) vcsCredentialsProvider).isNoneBlank()) {
                authManager = SVNWCUtil.createDefaultAuthenticationManager(null,
                        ((VcsUserNamePwdCredentialsProvider) vcsCredentialsProvider).getUserName(),
                        ((VcsUserNamePwdCredentialsProvider) vcsCredentialsProvider).getPassWord().toCharArray());
            }
        }
        return SVNClientManager.newInstance(options, authManager);
    }


}
