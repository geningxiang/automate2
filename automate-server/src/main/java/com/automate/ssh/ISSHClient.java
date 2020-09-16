package com.automate.ssh;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/19 20:58
 */
public interface ISSHClient {

    String getSshHost();

    Integer getSshPort();

    String getSshUser();

    String getSshPwd();
}
