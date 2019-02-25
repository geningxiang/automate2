package com.automate.ssh;

import com.automate.exec.ExecCommand;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/25 23:12
 */
public class SSHSessionTest {

    @Test
    public void doWork() throws Exception {
        SSHSession s = new SSHSession("47.100.63.232", 22, "root", "Genx@linux");

        s.doWork(sshConnection -> {
            System.out.println("lalala");
            ExecCommand execCommand = new ExecCommand("ps aux ww | grep -v grep | grep -E \"java|nginx|redis\"");

            sshConnection.exec(execCommand);

            System.out.println(execCommand.getOut());

            System.out.println(execCommand.getExitValue());
        });

        s.doWork(sshConnection -> {
            System.out.println("lalala");
        });

        s.doWork(sshConnection -> {
            System.out.println("lalala");
        });

        s.doWork(sshConnection -> {
            System.out.println("lalala");
        });
    }
}