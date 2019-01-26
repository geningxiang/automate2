package com.automate.vcs.git;

import com.automate.vcs.AbstractCVSHelper;
import com.automate.vcs.ICVSRepository;
import com.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 22:23
 */
public class GitHelper extends AbstractCVSHelper {


    public GitHelper(ICVSRepository repository) {
        super(repository);
    }

    /**
     * 初始化
     */
    public void init() throws GitAPIException {
        CloneCommand cloneCommand = Git.cloneRepository();
        if (StringUtils.isNotEmpty(this.userName) && StringUtils.isNotEmpty(this.passWord)) {
            cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.userName, this.passWord));
        }
        cloneCommand.setURI(this.remoteUrl).setDirectory(new File(this.localDir));
        //setCloneAllBranches(true).
        Git result = cloneCommand.setProgressMonitor(new JgitProgressMonitor()).call();
    }

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
