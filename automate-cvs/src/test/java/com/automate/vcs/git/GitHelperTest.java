package com.automate.vcs.git;

import com.alibaba.fastjson.JSON;
import com.automate.vcs.ICVSRepository;
import com.automate.vcs.vo.CommitLog;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

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
    public void init() throws GitAPIException {
        ICVSRepository repository = new CVSTestRepository(
                "E:\\work\\temp",
                "http://60.190.13.162:6104/quotation/QuotationServer.git",
                "genx",
                "ge10111011");

        GitHelper gitHelper = new GitHelper(repository);
        gitHelper.init();

    }
}