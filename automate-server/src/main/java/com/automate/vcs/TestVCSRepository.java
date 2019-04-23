package com.automate.vcs;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/16 16:53
 */
public class TestVCSRepository implements IVCSRepository {

    private int vcsType;
    private String vcsUrl;
    private String userName;
    private String passWord;


    @Override
    public int getVcsType() {
        return vcsType;
    }

    @Override
    public String getRemoteUrl() {
        return this.vcsUrl;
    }

    public void setVcsType(Integer vcsType) {
        this.vcsType = vcsType != null ? vcsType : 0;
    }

    public String getVcsUrl() {
        return vcsUrl;
    }

    public void setVcsUrl(String vcsUrl) {
        this.vcsUrl = vcsUrl;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
