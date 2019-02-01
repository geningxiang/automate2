package com.automate.exec;

import com.google.common.collect.ImmutableList;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 17:27
 */
public class ExecHelperTest {


    public static void main(String[] args) throws IllegalAccessException {

        ExecCommand execCommand = new ExecCommand(ImmutableList.of("ping www.baidu.com", "ping www.fcaimao.com"), new IExecStreamMonitor() {
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

        //execCommand.setTimeOut(TimeUnit.SECONDS, 3);

        ExecHelper.exec(execCommand);


    }
}