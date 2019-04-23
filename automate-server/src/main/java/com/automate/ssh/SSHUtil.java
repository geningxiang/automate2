package com.automate.ssh;

import com.automate.exec.ExecCommand;
import com.automate.exec.IExecStreamMonitor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/4/14 22:37
 */
public class SSHUtil {

    public static List<String[]> md5sum(SSHSession s, final String dir) throws Exception {
        List<String[]> list = new ArrayList(1024);
        String targetDir = dir;
        if (!targetDir.endsWith("/")) {
            targetDir += "/";
        }

        // 递归生成各文件的的MD5值
        ExecCommand execCommand = new ExecCommand("cd " + targetDir + " && " + "find ./ -type f -print0 | xargs -0 md5sum", new IExecStreamMonitor() {
            @Override
            public void onStart(String command) {

            }

            @Override
            public void onMsg(String line) {
                if (StringUtils.isNotBlank(line)) {
                    String[] ss = line.split("  ");
                    if (ss.length == 2) {
                        list.add(new String[]{ss[1].substring(2), ss[0]});
                    }
                }

            }

            @Override
            public void onEnd(int exitValue) {

            }
        });
        s.doWork(sshConnection -> sshConnection.exec(execCommand));

        if (execCommand.getExitValue() == 0) {
            //path 正序
            Collections.sort(list, Comparator.comparing(o -> o[0]));
            return list;
        } else {
            throw new RuntimeException("Process finished with exit code " + execCommand.getExitValue());
        }
    }
}
