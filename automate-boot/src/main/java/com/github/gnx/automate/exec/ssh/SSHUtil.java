//package com.github.gnx.automate.exec.ssh;
//
//import com.github.gnx.automate.exec.ExecCommand;
//import com.github.gnx.automate.exec.IExecStreamMonitor;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// *
// * @author: genx
// * @date: 2019/4/14 22:37
// */
//public class SSHUtil {
//    private static Logger logger = LoggerFactory.getLogger(SSHUtil.class);
//
//    public static List<String[]> sha256sum(SSHSession s, final String dir) throws Exception {
//        List<String[]> list = new ArrayList(1024);
//        s.doWork(sshConnection ->
//                list.addAll(sha256sum(sshConnection, dir))
//        );
//        return list;
//    }
//
//    public static List<String[]> sha256sum(SSHConnection s, final String dir) throws Exception {
//        List<String[]> list = new ArrayList(1024);
//        String targetDir = dir;
//        if (!targetDir.endsWith("/")) {
//            targetDir += "/";
//        }
//        String cmd = "cd " + targetDir + " && find ./ -type f -print0 | xargs -0 sha256sum";
//
//
//        logger.debug("[sha256sum]{}", cmd);
//
//        // 递归生成各文件的的MD5值
//        ExecCommand execCommand = new ExecCommand(cmd, new IExecStreamMonitor() {
//            @Override
//            public void onStart(String command) {
//
//            }
//
//            @Override
//            public void onMsg(String line) {
//                if (StringUtils.isNotBlank(line)) {
//                    String[] ss = line.split("  ");
//                    if (ss.length == 2 && ss[1].length() > 2) {
//                        //空目录是  -
//                        list.add(new String[]{ss[1].substring(2), ss[0]});
//                    }
//                }
//            }
//
//            @Override
//            public void onError(String line) {
//
//            }
//
//            @Override
//            public void onEnd(int exitValue) {
//
//            }
//        });
//
//        s.exec(execCommand);
//
//        if (execCommand.getExitValue() == 0) {
//            //path 正序
//            Collections.sort(list, Comparator.comparing(o -> o[0]));
//            return list;
//        } else {
//            throw new RuntimeException("Process finished with exit code " + execCommand.getExitValue());
//        }
//    }
//}
