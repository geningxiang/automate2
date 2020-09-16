package com.github.gnx.automate.vcs;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/27 23:24
 */
public class VcsUserNamePwdCredentialsProvider implements IVcsCredentialsProvider {
    private final String userName;
    private final String passWord;

    public VcsUserNamePwdCredentialsProvider(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public boolean isNoneBlank(){
        return StringUtils.isNoneBlank(userName, passWord);
    }
}
