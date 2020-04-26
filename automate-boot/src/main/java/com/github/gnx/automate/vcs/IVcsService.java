package com.github.gnx.automate.vcs;

import com.github.gnx.automate.contants.VcsType;
import com.github.gnx.automate.vcs.vo.CommitLog;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 21:36
 */
public interface IVcsService {

    /**
     * 查询提交记录时  默认查200条
     */
    int DEFAULT_LIMIT = 200;

    VcsType getVcsType();

    /**
     * 测试 vcs 连接
     * @param remoteUrl
     * @param vcsCredentialsProvider
     * @throws Exception
     */
    void test(String remoteUrl, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception;

    /**
     * clone
     * @param remoteUrl
     * @param localDir
     * @param vcsCredentialsProvider
     * @return
     * @throws Exception
     */
    int clone(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception;


    /**
     *
     * @param projectId
     * @param remoteUrl
     * @param localDir
     * @param vcsCredentialsProvider
     * @return 更新的分支数
     * @throws Exception
     */
    default int update(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception {
        if (localDir.exists()) {
            return this.doUpdate(projectId, remoteUrl, localDir, vcsCredentialsProvider);
        } else {
            return this.clone(projectId, remoteUrl, localDir, vcsCredentialsProvider);
        }
    }

    /**
     * 更新仓库
     * @param remoteUrl
     * @param localDir
     * @param vcsCredentialsProvider
     * @return
     * @throws Exception
     */
    int doUpdate(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider) throws Exception;

    default List<CommitLog> commitLog(String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider, String branch) throws Exception {
        return this.commitLog(remoteUrl, localDir, vcsCredentialsProvider, branch, null, DEFAULT_LIMIT);
    }

    /**
     * 查看提交日志
     * @param remoteUrl
     * @param localDir
     * @param vcsCredentialsProvider
     * @param startId
     * @param limit
     * @return
     */
    List<CommitLog> commitLog(String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider, String branch, String startId, int limit) throws Exception;


    /**
     * 切换分支
     * @param branch
     * @param commitId
     * @return
     */
    String checkOut(int projectId, String remoteUrl, File localDir, IVcsCredentialsProvider vcsCredentialsProvider, String branch, String commitId) throws Exception;
}
