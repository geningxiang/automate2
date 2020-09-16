package com.github.gnx.automate.vcs.git.impl;

import com.github.gnx.automate.vcs.IVcsCredentialsProvider;
import com.github.gnx.automate.vcs.IVcsService;
import com.github.gnx.automate.vcs.VcsUserNamePwdCredentialsProvider;
import com.github.gnx.automate.vcs.git.GitServiceImpl;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/27 23:37
 */

public class GitServiceImplTest {

    private String url = "http://60.190.13.162:6104/caimao-lottery/CaimaoTouch.git";
    private IVcsCredentialsProvider vcsCredentialsProvider = new VcsUserNamePwdCredentialsProvider("genx", "ge10111011");
    private File dir = new File("E:/work/automate/CaimaoTouch/");

    private IVcsService vcsService = new GitServiceImpl();

    @Test
    public void test() throws Exception {

        vcsService.clone(0, url, dir, vcsCredentialsProvider);
    }

    @Test
    public void branchList() throws IOException, GitAPIException {
        File file = new File("E:/work/automate/CaimaoTouch/.git");
        int c = 0;
        List<Ref> call = new Git(new FileRepository(file)).branchList().call();
        for (Ref ref : call) {
            System.out.println( ref.getName() + " "
                    + ref.getObjectId().name());
            c++;
        }
        System.out.println("Number of branches: " + c);
    }


    @Test
    public void update() throws Exception {
        vcsService.doUpdate(0, "" ,  dir, vcsCredentialsProvider);
    }


    @Test
    public void commitList() throws IOException, GitAPIException {
        File file = new File("E:/work/automate/CaimaoTouch/.git");

        Git git = Git.wrap(new FileRepository(file));

        git.checkout().setName("develop").call();


        ObjectId objId = git.getRepository().resolve("2e3ae58f8bbfa2e6b62c1db5fb7564f3e0b4ca33");

        //.all()       标识查看所有分支的日志
        Iterator<RevCommit> commits = git.log().add(objId).setMaxCount(10).call().iterator();



        for (Iterator<RevCommit> it = commits; it.hasNext(); ) {
            RevCommit commit = it.next();
            System.out.println(commit.getId().getClass().getName());
            System.out.println(commit.getId().toObjectId() + " | " + new Date(commit.getCommitTime() * 1000) + " | " + commit.getFullMessage());
        }

        git.close();
    }
}
