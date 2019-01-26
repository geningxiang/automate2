package com.automate.vcs;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/1/26 23:25
 */
public interface ICVSRepository {

    String getLocalDir();

    String getRemoteUrl();

    default String getUserName(){
        return "";
    }

    default String getPassWord(){
        return "";
    }
}
