package com.automate.cmd;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 0:46
 */
public class Test {

    public static void main(String[] args) {
        LocalCommandExecutor cmd = new LocalCommandExecutorImpl();

        ExecuteResult executeResult = cmd.executeCommand("ping www.baidu.com -t", 10000);


        System.out.println(executeResult.getExitCode());

    }
}
