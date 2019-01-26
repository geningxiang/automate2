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

    List<CommitLog> commitLogs() throws Exception;
}
