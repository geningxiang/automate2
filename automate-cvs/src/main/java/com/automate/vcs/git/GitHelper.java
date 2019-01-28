package com.automate.vcs.git;

import com.automate.vcs.AbstractCVSHelper;
import com.automate.vcs.ICVSRepository;
import com.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 22:23
 */
public class GitHelper extends AbstractCVSHelper {

    private static final Logger logger = LoggerFactory.getLogger(GitHelper.class);

    private CredentialsProvider credentialsProvider = null;

    public GitHelper(ICVSRepository repository) {
        super(repository);
        if (StringUtils.isNotEmpty(this.userName) && StringUtils.isNotEmpty(this.passWord)) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(this.userName, this.passWord);
        }
    }

    /**
     * 初始化 clone项目
     */
    @Override
    public void init() throws Exception {
        CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setCredentialsProvider(this.credentialsProvider);
        cloneCommand.setURI(this.remoteUrl).setDirectory(new File(this.localDir));
        cloneCommand.setCloneAllBranches(true);
        cloneCommand.setProgressMonitor(new JgitProgressMonitor());
        cloneCommand.call();
    }

    /**
     * 同步项目
     * @throws Exception
     */
    @Override
    public void update() throws Exception {
        update(null);
    }

    /**
     * 同步项目的一个分支
     * @param branchName 分支名称
     * @throws Exception
     */
    @Override
    public void update(String branchName) throws Exception {
        FileRepository db = openFileRepository();
        try {
            Git git = Git.wrap(db);
            Map<String, Integer> localBranchMap = new HashMap(8);
            //查看本地分支
            List<Ref> list = git.branchList().call();
            for (Ref ref : list) {
                localBranchMap.put(ref.getName().substring(GitContants.BRANCH_NAME_PREFIX_LEN), null);
            }
            //查询远程分支
            Collection<Ref> refs = git.lsRemote().setTags(false).setHeads(false).setCredentialsProvider(this.credentialsProvider).call();
            int update = 0;
            for (Ref ref : refs) {
                if (ref.getName().startsWith(GitContants.BRANCH_NAME_PREFIX)) {
                    String remoteBranchName = ref.getName().substring(GitContants.BRANCH_NAME_PREFIX_LEN);
                    if(StringUtils.isEmpty(branchName) || branchName.equals(remoteBranchName)) {
                        CheckoutCommand checkoutCommand = git.checkout().setName(remoteBranchName);
                        if (!localBranchMap.containsKey(remoteBranchName)) {
                            //新建分支
                            logger.info("{},新建分支:{}", this.localDir, remoteBranchName);
                            checkoutCommand.setCreateBranch(true);
                        }
                        checkoutCommand.call();
                        //reset hard
                        git.reset().setMode(ResetCommand.ResetType.HARD).call();
                        //从远程库 pull 代码
                        git.pull().setCredentialsProvider(this.credentialsProvider).call();
                        update++;
                    }
                }
            }
            if(update == 0) {
                throw new IllegalArgumentException("the branchName is not correct?");
            }
        } finally {
            db.close();
        }
    }

    /**
     * 查询单个分支的所有提交历史(本地)
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<CommitLog> commitLogs() throws Exception {
        FileRepository db = openFileRepository();
        try {
            Git git = Git.wrap(db);
            Iterator<RevCommit> commits = git.log().all().call().iterator();
            List<CommitLog> list = new ArrayList<>(256);
            for (Iterator<RevCommit> it = commits; it.hasNext(); ) {
                RevCommit commit = it.next();
                list.add(JgitFormat.parse(commit));
            }
            return list;
        } finally {
            db.close();
        }
    }

    private FileRepository openFileRepository() throws IOException {
        File file = new File(this.localDir);
        if (!file.exists()) {
            //TODO clone 远程库
            throw new RuntimeException("文件路径不存在：" + this.localDir);
        }
        if (!this.localDir.endsWith(GitContants.DOT_GIT)) {
            file = new File(this.localDir + File.separator + GitContants.DOT_GIT);
        }
        if (!file.exists()) {
            throw new RuntimeException("未处于版本控制下：" + this.localDir);
        }
        return new FileRepository(file);
    }
}
