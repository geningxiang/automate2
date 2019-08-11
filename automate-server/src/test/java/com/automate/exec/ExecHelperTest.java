package com.automate.exec;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 17:27
 */
public class ExecHelperTest {


    public static void main(String[] args) throws IllegalAccessException {

        File dir = new File("E:/work/1");
        ExecCommand execCommand = new ExecCommand(ImmutableList.of("echo %path%"), null, dir, new IExecStreamMonitor() {
            @Override
            public void onStart(String commond) {
                System.out.println(commond);
            }

            @Override
            public void onMsg(String line) {
                System.out.println(line);
            }

            @Override
            public void onError(String line) {
                System.err.println(line);
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