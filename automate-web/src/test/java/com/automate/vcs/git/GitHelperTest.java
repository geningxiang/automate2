package com.automate.vcs.git;

import com.automate.vcs.ICVSRepository;
import com.automate.vcs.vo.CommitLog;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
        GitHelper gitHelper = new GitHelper(new ICVSRepository() {
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

            public String getUserName(){
                return "genx";
            }

            public String getPassWord(){
                return "ge10111011";
            }
        });

         List<CommitLog> list =  gitHelper.commitLogs("master");
        for (CommitLog commitLog : list) {
            System.out.println(commitLog.getId());
        }

    }

    @Test
    public void checkOut() throws Exception {
        GitHelper gitHelper = new GitHelper(new ICVSRepository() {
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

            public String getUserName(){
                return "genx";
            }

            public String getPassWord(){
                return "ge10111011";
            }
        });

        boolean result = gitHelper.checkOut("master", "76e5731e0ef0cd49374c94bdd2b773d34071d11e");
        //        System.out.println(result);

//        boolean result = gitHelper.checkOut("develop1", "06cd27b092b798dc8926f076a619dd482e9ecee0");

//        gitHelper.update();


    }

    @Test
    public void isLocalRepositoryExist() {

    }
}