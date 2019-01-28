package com.automate.vcs.git;

import com.alibaba.fastjson.JSON;
import com.automate.vcs.ICVSRepository;
import com.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 22:24
 */

public class GitHelperTest {


    @Before
    public void before() throws IOException {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void commitLogs() throws Exception {
        ICVSRepository repository = new CVSTestRepository("D:\\idea-workspace\\CaimaoQuotation\\QuotationServer", null, null, null);
        GitHelper gitHelper = new GitHelper(repository);
        List<CommitLog> list = gitHelper.commitLogs();
        for (CommitLog commitLog : list) {
            System.out.println(JSON.toJSONString(commitLog));
        }
    }

    @Test
    public void init() throws Exception {
        ICVSRepository repository = new CVSTestRepository(
                "E:\\work\\temp",
                "http://60.190.13.162:6104/quotation/QuotationServer.git",
                "genx",
                "ge10111011");

        GitHelper gitHelper = new GitHelper(repository);
        gitHelper.init();

    }

    @Test
    public void test() throws IOException, GitAPIException {
        FileRepository db = new FileRepository(new File("E:/work/temp/.git"));

        Git git = Git.wrap(db);
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new UsernamePasswordCredentialsProvider("genx", "ge10111011");
        Map<String, String> localBranchMap = new HashMap<>();
        List<Ref> list = git.branchList().call();
        for (Ref ref : list) {
            System.out.println(ref.getName());
            localBranchMap.put(ref.getName().substring(11), ref.getObjectId().toObjectId().getName());
        }

        System.out.println(localBranchMap.entrySet());

//        git.checkout().setName("").call();
//
//        git.reset().call();


        Collection<Ref> refs = git.lsRemote().setTags(false).setHeads(false).setCredentialsProvider(usernamePasswordCredentialsProvider).call();
        for (Ref ref : refs) {
//            System.out.println(ref.getName() + " | " + ref.isPeeled() + " | " + ref.isSymbolic());
            if(ref.getName().startsWith("refs/heads/")) {
                String branchName = ref.getName().substring(11);
                String id = localBranchMap.get(branchName);
                if(id == null){
                    System.out.println("新建：" + branchName);
                    git.checkout().setName(branchName).setCreateBranch(true).call();
                } else {
                    System.out.println(id);
                    System.out.println(ref.isPeeled());
                    System.out.println("更新：" + branchName);
                    git.checkout().setName(branchName).call();

                }
                git.reset().setMode(ResetCommand.ResetType.HARD).call();
                git.pull().setCredentialsProvider(usernamePasswordCredentialsProvider).call();
            }
        }
        db.close();
    }


}
