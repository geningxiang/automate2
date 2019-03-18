package com.automate.vcs.svn;

import com.alibaba.fastjson.JSON;
import com.automate.vcs.IVCSRepository;
import com.automate.vcs.git.GitHelper;
import com.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;
import org.tmatesoft.svn.core.wc.admin.SVNLookClient;
import org.tmatesoft.svn.core.wc.admin.SVNSyncInfo;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/18 17:16
 */
public class SvnHelperTest {
    private String remoteUrl = "https://www.kxtan.com/svn/projects/apps/client";
    private String dir = "E:/work/CaimaoClient";
    private String userName = "";
    private String passWord = "";
    private SvnHelper svnHelper = new SvnHelper(new IVCSRepository() {
        @Override
        public Integer getId() {
            return 1;
        }

        @Override
        public String getLocalDir() {
            return dir;
        }

        @Override
        public String getRemoteUrl() {
            return remoteUrl;
        }

        @Override
        public String getUserName() {
            return userName;
        }

        @Override
        public String getPassWord() {
            return passWord;
        }
    });

    @Test
    public void init() throws Exception {
        svnHelper.init();
    }

    @Test
    public void commitLogs() throws Exception {
        List<CommitLog> list = svnHelper.commitLogs("");
        for (CommitLog commitLog : list) {
            System.out.println(JSON.toJSONString(commitLog));
        }
    }

    @Test
    public void checkout() throws Exception {
        svnHelper.checkOut("", "17297");
    }

    @Test
    public void checkoutHead() throws Exception {
        svnHelper.checkOut("", null);
    }

    @Test
    public void update() throws Exception {
        svnHelper.update();
    }

    @Test
    public void doList() throws SVNException {
        SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(remoteUrl));
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(null, userName, passWord.toCharArray());
        repository.setAuthenticationManager(authManager);
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager svnClientManager = SVNClientManager.newInstance(options, authManager);

        SVNLogClient svnLogClient = svnClientManager.getLogClient();

        //查询远程仓库 文件列表
        svnLogClient.doList(SVNURL.parseURIEncoded(remoteUrl), SVNRevision.HEAD, SVNRevision.HEAD, true, SVNDepth.INFINITY, -1, svnDirEntry -> {
            if (svnDirEntry.getKind() == SVNNodeKind.DIR) {
                System.out.println(svnDirEntry.getURL());
            }
        });
        repository.closeSession();
    }


}