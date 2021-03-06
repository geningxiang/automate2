package com.automate.vcs;

import com.automate.vcs.vo.CommitLog;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 22:21
 */
public interface IVCSHelper {

    /**
     * 初始化项目  clone
     * @throws Exception
     */
    Set<String> init() throws Exception;

    /**
     * 更新整个项目
     * @throws Exception
     * @return 有变化的分支列表
     */
    Set<String> update() throws Exception;

    /**
     * 更新项目的一个分支
     * @param branchName
     * @throws Exception
     * @return 有变化的分支列表
     */
    Set<String> update(String branchName) throws Exception;

    /**
     * 查看提交记录(本地的)
     * @param  branchName 分支名
     * @return  倒序的  最新的在前面
     * @throws Exception
     */
    List<CommitLog> commitLogs(String branchName) throws Exception;

    /**
     * 检出指定分支、指定版本
     * @param branchName
     * @param commitId
     * @return
     * @throws Exception
     */
    String checkOut(String branchName, String commitId) throws Exception;

    void testConnetction() throws Exception;

    boolean isLocalRepositoryExist();
}
