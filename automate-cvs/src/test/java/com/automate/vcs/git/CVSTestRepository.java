package com.automate.vcs.git;

import com.automate.vcs.ICVSRepository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 23:32
 */
public class CVSTestRepository implements ICVSRepository {
    private String localDir;
    private String remoteUrl;
    private String userName;
    private String passWord;

    public CVSTestRepository(String localDir, String remoteUrl, String userName, String passWord) {
        this.localDir = localDir;
        this.remoteUrl = remoteUrl;
        this.userName = userName;
        this.passWord = passWord;
    }

    @Override
    public String getLocalDir() {
        return localDir;
    }

    @Override
    public String getRemoteUrl() {
        return remoteUrl;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassWord() {
        return passWord;
    }
}
