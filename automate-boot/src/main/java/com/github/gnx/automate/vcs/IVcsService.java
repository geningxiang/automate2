package com.github.gnx.automate.vcs;

import com.github.gnx.automate.contants.VcsType;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/26 21:36
 */
public interface IVcsService {

    VcsType getVcsType();

    /**
     * 测试 vcs 连接
     * @param url
     * @param name
     * @param pwd
     * @throws Exception
     */
    void test(String url, String name, String pwd) throws Exception;



}
