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
public abstract class AbstractCVSHelper implements ICVSHelper {
    protected final String localDir;
    protected final String remoteUrl;
    protected final String userName;
    protected final String passWord;

    public AbstractCVSHelper(ICVSRepository repository){
        this.localDir = repository.getLocalDir();
        this.remoteUrl = repository.getRemoteUrl();
        this.userName = repository.getUserName();
        this.passWord = repository.getPassWord();
    }


}
