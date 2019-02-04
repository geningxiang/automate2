package com.automate.exec;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/4 23:55
 */
public class ExecStreamPrintMonitor implements IExecStreamMonitor {
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
}
