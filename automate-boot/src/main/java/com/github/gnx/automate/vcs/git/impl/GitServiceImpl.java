package com.github.gnx.automate.vcs.git.impl;

import com.github.gnx.automate.contants.VcsType;
import com.github.gnx.automate.event.IEventPublisher;
import com.github.gnx.automate.event.vo.BranchUpdatedEvent;
import com.github.gnx.automate.vcs.IVcsCredentialsProvider;
import com.github.gnx.automate.vcs.IVcsService;
import com.github.gnx.automate.vcs.VcsUserNamePwdCredentialsProvider;
import com.github.gnx.automate.vcs.git.JgitProgressMonitor;
import com.github.gnx.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 21:37
 */
@Service
public class GitServiceImpl implements IVcsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IEventPublisher eventPublisher;

    /**
     * 仓库文件夹后缀 .git
     */
    private final String DOT_GIT = ".git";

    private final String MASTER = "master";

    /**
     * 分支名 前缀
     */
    private final String BRANCH_NAME_PREFIX = "refs/heads/";

    /**
     * 分支名前缀 长度
     */
    private final int BRANCH_NAME_PREFIX_LEN = 11;


    @Override
    public VcsType getVcsType() {
        return VcsType.GIT;
    }

    @Override
    public void test(String url, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception {
        LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository().setCredentialsProvider(buildCredentialsProvider(vcsCredentialsProvider));
        lsRemoteCommand.setRemote(url);
        lsRemoteCommand.call();
    }

    @Override
    public int clone(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception {
        if (localDir.exists()) {
            throw new RuntimeException("文件夹已存在:" + localDir.getAbsolutePath());
        }
        CloneCommand cloneCommand = Git.cloneRepository().setCredentialsProvider(buildCredentialsProvider(vcsCredentialsProvider));
        cloneCommand.setURI(remoteUrl).setDirectory(localDir);
        //TODO 为啥没用呢
        cloneCommand.setCloneAllBranches(true);
        cloneCommand.setProgressMonitor(new JgitProgressMonitor());
        logger.debug("开始 git clone, url: {}", remoteUrl);
        cloneCommand.call().close();
        //git clone后 调用一遍 update 同步所有分支
        return doUpdate(projectId, remoteUrl, localDir, vcsCredentialsProvider, true);
    }

    @Override
    public int doUpdate(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception {
        return this.doUpdate(projectId, remoteUrl, localDir, vcsCredentialsProvider, false);
    }


    public int doUpdate(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider, boolean isClone) throws Exception {
        int updated = 0;
        FileRepository fileRepository = null;
        Git git = null;
        try {
            fileRepository = openFileRepository(localDir);
            git = Git.wrap(fileRepository);
            Map<String, String> localBranchMap = new HashMap(8);
            //查看本地分支
            List<Ref> list = git.branchList().call();
            for (Ref ref : list) {
                localBranchMap.put(ref.getName().substring(BRANCH_NAME_PREFIX_LEN), ref.getObjectId().toObjectId().getName());
            }

            //查询远程分支
            Collection<Ref> refs = git.lsRemote().setTags(false).setHeads(false).setCredentialsProvider(buildCredentialsProvider(vcsCredentialsProvider)).call();
            for (Ref ref : refs) {
                if (ref.getName().startsWith(BRANCH_NAME_PREFIX)) {
                    String branchName = ref.getName().substring(BRANCH_NAME_PREFIX_LEN);
                    logger.debug("check out {} ", branchName);
                    CheckoutCommand checkoutCommand = git.checkout().setName(branchName);
                    String id = localBranchMap.get(branchName);
                    if (id == null) {
                        //新建分支
                        logger.info("{},新建分支:{}", localDir, branchName);
                        checkoutCommand.setCreateBranch(true);
                    }
                    checkoutCommand.call();

                    //reset hard
                    git.reset().setMode(ResetCommand.ResetType.HARD).setRef("origin/" + branchName).call();

                    //从远程库 pull 代码
                    PullResult pullResult = git.pull().setRemoteBranchName(branchName).setCredentialsProvider(buildCredentialsProvider(vcsCredentialsProvider)).call();

                    boolean isUpdate = false;
                    if (pullResult.isSuccessful()) {
                        if (id == null || !id.equals(pullResult.getMergeResult().getNewHead().name())) {
                            logger.info("{} pushed, before:{}, after:{}", branchName, id, pullResult.getMergeResult().getNewHead().name());
                            updated++;
                            isUpdate = true;
                        } else if (isClone && MASTER.equals(branchName)) {
                            updated++;
                            isUpdate = true;
                        }

                        if (isUpdate) {
                            List<CommitLog> commitLogList = new ArrayList(IVcsService.DEFAULT_LIMIT);
                            Iterator<RevCommit> commits = git.log().setMaxCount(IVcsService.DEFAULT_LIMIT).call().iterator();
                            while (commits.hasNext()) {
                                RevCommit revCommit = commits.next();
                                commitLogList.add(parse(revCommit));
                            }
                            eventPublisher.publishEvent(new BranchUpdatedEvent(projectId, branchName, commitLogList));
                        }
                    }

                }
            }
        } finally {
            if (fileRepository != null) {
                // Git.wrap( 实例化  fileRepository 不能自动关闭
                fileRepository.close();
            }
            if (git != null) {
                git.close();
            }
        }
        return updated;
    }

//    public List<VcsBranch> branchList(String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception {
//        FileRepository fileRepository = null;
//        Git git = null;
//        try {
//            fileRepository = openFileRepository(localDir);
//            git = Git.wrap(fileRepository);
//            List<Ref> call = git.branchList().call();
//            List<VcsBranch> list = new ArrayList();
//            for (Ref ref : call) {
//                list.add(new VcsBranch(ref.getName().substring(BRANCH_NAME_PREFIX_LEN), ref.getObjectId().name()));
//            }
//            return list;
//        }finally {
//            if (fileRepository != null) {
//                // Git.wrap( 实例化  fileRepository 不能自动关闭
//                fileRepository.close();
//            }
//            if (git != null) {
//                git.close();
//            }
//        }
//    }

    @Override
    public List<CommitLog> commitLog(String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider, String branch, String startId, int limit) throws Exception {
        FileRepository fileRepository = null;
        Git git = null;
        try {
            fileRepository = openFileRepository(localDir);
            git = Git.wrap(fileRepository);
            LogCommand logCommand = git.log();
            if (startId != null) {
                logCommand.add(git.getRepository().resolve(startId));
            }
            Iterator<RevCommit> commits = logCommand.setMaxCount(limit).call().iterator();
            List<CommitLog> list = new ArrayList<>(limit);
            for (Iterator<RevCommit> it = commits; it.hasNext(); ) {
                RevCommit commit = it.next();
                list.add(parse(commit));
            }
            return list;
        }finally {
            if (fileRepository != null) {
                // Git.wrap( 实例化  fileRepository 不能自动关闭
                fileRepository.close();
            }
            if (git != null) {
                git.close();
            }
        }
    }

    @Override
    public String checkOut(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider, String branch, String commitId) throws Exception {
        FileRepository fileRepository = null;
        Git git = null;
        try {
            fileRepository = openFileRepository(localDir);
            git = Git.wrap(fileRepository);

            git.checkout().setName(branch).call();
            String id;
            if(StringUtils.isNotBlank(commitId)) {
                Ref resetCommand = git.reset().setMode(ResetCommand.ResetType.HARD).setRef(commitId).call();
                id = resetCommand.getObjectId().toObjectId().name();
            } else {
                //reset hard
                Ref ref = git.reset().setMode(ResetCommand.ResetType.HARD).setRef("origin/" + branch).call();
                id = ref.getObjectId().toObjectId().name();
            }
            logger.debug("localDir:{},branch:{},commitId:{};切换到{}", localDir, branch, commitId, id);
            return id;
        } finally {
            if (fileRepository != null) {
                // Git.wrap( 实例化  fileRepository 不能自动关闭
                fileRepository.close();
            }
            if (git != null) {
                git.close();
            }
        }
    }


    private CommitLog parse(RevCommit revCommit) {
        CommitLog commitLog = new CommitLog();
        commitLog.setId(revCommit.toObjectId().getName());
        commitLog.setAuthor(revCommit.getAuthorIdent().getName());
        commitLog.setEmail(revCommit.getAuthorIdent().getEmailAddress());
        commitLog.setCommitTime(revCommit.getCommitTime() * 1000L);
        commitLog.setMsg(revCommit.getFullMessage());
        return commitLog;
    }

    private CredentialsProvider buildCredentialsProvider(IVcsCredentialsProvider vcsCredentialsProvider) {
        if (vcsCredentialsProvider != null && vcsCredentialsProvider instanceof VcsUserNamePwdCredentialsProvider) {
            if (((VcsUserNamePwdCredentialsProvider) vcsCredentialsProvider).isNoneBlank()) {
                return new UsernamePasswordCredentialsProvider(
                        ((VcsUserNamePwdCredentialsProvider) vcsCredentialsProvider).getUserName(),
                        ((VcsUserNamePwdCredentialsProvider) vcsCredentialsProvider).getPassWord());
            }
        }
        return null;
    }


    private FileRepository openFileRepository(File file) throws IOException {
        if (!file.exists()) {
            //TODO clone 远程库
            throw new RuntimeException("文件路径不存在：" + file.getAbsolutePath());
        }
        if (file.isDirectory()) {
            file = new File(file.getAbsolutePath() + File.separator + DOT_GIT);
        }
        if (!file.exists()) {
            throw new RuntimeException("未处于版本控制下：" + file.getAbsolutePath());
        }
        return new FileRepository(file);
    }
}
