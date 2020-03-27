package com.github.gnx.automate.vcs.git.impl;

import com.github.gnx.automate.contants.VcsType;
import com.github.gnx.automate.vcs.IVcsService;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 21:37
 */
@Service
public class GitServiceImpl implements IVcsService {


    @Override
    public VcsType getVcsType() {
        return VcsType.GIT;
    }

    @Override
    public void test(String url, String userName, String passWord) throws Exception {
        LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository();
        if (StringUtils.isNoneBlank(userName, passWord)) {
            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(userName, passWord);
            lsRemoteCommand.setCredentialsProvider(credentialsProvider);
        }
        lsRemoteCommand.setRemote(url);
        lsRemoteCommand.setTimeout(10);
        lsRemoteCommand.call();
    }
}
