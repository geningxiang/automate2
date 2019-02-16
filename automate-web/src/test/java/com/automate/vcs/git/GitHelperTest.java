package com.automate.vcs.git;

import com.automate.vcs.IVCSRepository;
import com.automate.vcs.vo.CommitLog;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/5 20:52
 */
public class GitHelperTest {

    @Test
    public void init() {
    }

    @Test
    public void update() {
    }


    @Test
    public void commitLogs() throws Exception {
        GitHelper gitHelper = new GitHelper(new IVCSRepository() {
            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public String getLocalDir() {
                return "E:\\automate-data\\sourcecode\\1";
            }

            @Override
            public String getRemoteUrl() {
                return "http://60.190.13.162:6104/genx/SpringBootDemo.git";
            }

            @Override
            public String getUserName() {
                return "genx";
            }

            @Override
            public String getPassWord() {
                return "ge10111011";
            }
        });

        List<CommitLog> list = gitHelper.commitLogs("master");
        for (CommitLog commitLog : list) {
            System.out.println(commitLog.getId());
        }

    }

    @Test
    public void checkOut() throws Exception {
        GitHelper gitHelper = new GitHelper(new IVCSRepository() {
            @Override
            public Integer getId() {
                return null;
            }

            @Override
            public String getLocalDir() {
                return "E:\\automate-data\\sourcecode\\1";
            }

            @Override
            public String getRemoteUrl() {
                return "http://60.190.13.162:6104/genx/SpringBootDemo.git";
            }

            @Override
            public String getUserName() {
                return "genx";
            }

            @Override
            public String getPassWord() {
                return "ge10111011";
            }
        });

        gitHelper.checkOut("master", "76e5731e0ef0cd49374c94bdd2b773d34071d11e");
        //        System.out.println(result);

//        boolean result = gitHelper.checkOut("develop1", "06cd27b092b798dc8926f076a619dd482e9ecee0");

//        gitHelper.update();


    }

    @Test
    public void isLocalRepositoryExist() {

    }

    @Test
    public void testConnection(){
        String remoteUrl = "http://60.190.13.122:6104/genx1/SpringBootDemo.git";
        String userName = "genx";
        String passWord = "ge101110112";

        CredentialsProvider credentialsProvider = null;
        if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(passWord)) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(userName, passWord);
        }

        LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository();

        lsRemoteCommand.setCredentialsProvider(credentialsProvider);
        lsRemoteCommand.setRemote(remoteUrl);

        lsRemoteCommand.setTimeout(3);
        Collection<Ref> list = null;
        try {
            list = lsRemoteCommand.call();

            for (Ref ref : list) {
                System.out.println(ref);
            }
        } catch (GitAPIException e) {
            e.printStackTrace();

            System.out.println(e.getMessage());

            //http://60.190.13.162:6104/genx/SpringBootDemo.git: Authentication is required but no CredentialsProvider has been registered
            //http://60.190.13.162:6104/genx/SpringBootDemo.git: authentication not supported

            //NoRemoteRepositoryException
            //http://60.190.13.162:6104/genx1/SpringBootDemo.git: http://60.190.13.162:6104/genx1/SpringBootDemo.git/info/refs?service=git-upload-pack not found
        }

    }
}