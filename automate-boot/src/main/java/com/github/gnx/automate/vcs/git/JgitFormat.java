package com.github.gnx.automate.vcs.git;


import com.github.gnx.automate.vcs.vo.CommitLog;
import com.github.gnx.automate.vcs.vo.Person;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 23:15
 */
public class JgitFormat {

    public static CommitLog parse(RevCommit revCommit){
        if(revCommit == null) {
            return null;
        }
        CommitLog commitLog = new CommitLog();
        commitLog.setId(revCommit.toObjectId().getName());
        commitLog.setAuthor(parse(revCommit.getAuthorIdent()));
        commitLog.setCommitter(parse(revCommit.getCommitterIdent()));
        commitLog.setCommitTime(revCommit.getCommitTime() * 1000L);
        commitLog.setMsg(revCommit.getFullMessage());
        return commitLog;
    }

    public static Person parse(PersonIdent personIdent){
        if(personIdent != null){
            return new Person(personIdent.getName(), personIdent.getEmailAddress());
        } else {
            return null;
        }
    }
}
