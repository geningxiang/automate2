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
        List<CommitLog> list = gitHelper.commitLogs("master");
        for (CommitLog commitLog : list) {
            System.out.println(JSON.toJSONString(commitLog));
        }
    }

      @Test
    public void init() throws Exception {
        ICVSRepository repository = new CVSTestRepository(
                "E:\\work\\SpringBootDemo",
                "http://60.190.13.162:6104/genx/SpringBootDemo.git",
                "genx",
                "ge10111011");

        GitHelper gitHelper = new GitHelper(repository);
        gitHelper.init();
    }

    @Test
    public void update() throws Exception {
        ICVSRepository repository = new CVSTestRepository(
                "E:\\work\\SpringBootDemo",
                "http://60.190.13.162:6104/genx/SpringBootDemo.git",
                "genx",
                "ge10111011");


        GitHelper gitHelper = new GitHelper(repository);
        List<String> updatedBranchList = gitHelper.update();
        System.out.println(StringUtils.join(updatedBranchList, ","));
    }



}
