package com.automate.cmd;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 17:27
 */
public class CmdHelperTest {
    public static void main(String[] args) {

        ExecuetCommand execuetCommand = new ExecuetCommand("ping www.baidu.com -t", new ICmdStreamMonitor() {
            @Override
            public void onStart(String commond) {
                System.out.println(commond);
            }

            @Override
            public void onMsg(String line) {
                System.out.println(line);
            }

            @Override
            public void onEnd(int exitValue) {
                System.out.println("finished " + exitValue);
            }
        });

        execuetCommand.setTimeOut(TimeUnit.SECONDS, 3);

        CmdHelper.exec(execuetCommand);


    }
}