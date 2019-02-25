package com.automate.ssh;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/25 23:06
 */
public interface SSHWork {

    void execute(SSHConnection sshConnection) throws Exception;
}
