package com.automate.vcs.svn;

import com.automate.vcs.vo.CommitLog;
import com.automate.vcs.vo.Person;
import org.tmatesoft.svn.core.SVNLogEntry;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 23:15
 */
public class SvnFormat {

    public static CommitLog parse(SVNLogEntry svnLogEntry) {
        if (svnLogEntry == null) {
            return null;
        }
        CommitLog commitLog = new CommitLog();
        commitLog.setId(String.valueOf(svnLogEntry.getRevision()));

        commitLog.setAuthor(new Person(svnLogEntry.getAuthor(), ""));
        commitLog.setCommitter(new Person(svnLogEntry.getAuthor(), ""));
        commitLog.setCommitTime(svnLogEntry.getDate().getTime());
        commitLog.setMsg(svnLogEntry.getMessage());
        return commitLog;
    }

}
