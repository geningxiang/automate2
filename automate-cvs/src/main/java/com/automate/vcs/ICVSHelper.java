package com.automate.vcs;

import com.automate.vcs.vo.CommitLog;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 22:21
 */
public interface ICVSHelper {

    /**
     * 初始化项目  clone
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * 更新整个项目
     * @throws Exception
     */
    void update() throws Exception;

    /**
     * 更新项目的一个分支
     * @param branchName
     * @throws Exception
     */
    void update(String branchName) throws Exception;

    /**
     * 查看提交记录(本地的)
     * @return
     * @throws Exception
     */
    List<CommitLog> commitLogs() throws Exception;
}
