package com.github.gnx.automate.exec.local;

import com.github.gnx.automate.common.Charsets;
import com.github.gnx.automate.common.IMsgListener;
import com.github.gnx.automate.exec.ExecStreamReader;
import com.github.gnx.automate.exec.IExecConnection;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 20:18
 */
public class LocalExecConnection implements IExecConnection {

    private static Logger logger = LoggerFactory.getLogger(LocalExecConnection.class);

    private final int TIME_OUT = 120;

    @Override
    public int exec(String cmd, IMsgListener execListener) throws Exception {

        final Process process;

        String[] cmds = new String[3];
        Charset charset = Charsets.UTF_8;
        if (SystemUtils.IS_OS_WINDOWS) {
            cmds[0] = "cmd.exe";
            cmds[1] = "/c";
            charset = Charsets.UTF_GBK;
        } else {
            /*
            bash -c
            /bin/sh -c
             */
            cmds[0] = "bash";
            cmds[1] = "-c";
        }

        cmds[2] = cmd;
        process = Runtime.getRuntime().exec(cmds);
        ExecStreamReader.submit(process.getInputStream(), charset, execListener);
        ExecStreamReader.submit(process.getErrorStream(), charset, execListener);
        if (!process.waitFor(TIME_OUT, TimeUnit.SECONDS)) {
            //超时取消

            if (SystemUtils.IS_OS_WINDOWS) {
                //windows 下 暂时没有找到 停止的办法
                logger.warn("windows系统下无法确保能够终止process");
                process.destroy();
            } else {
                //在 ubuntu 中 测试过 有效
                process.destroy();
//                Runtime.getRuntime().exec("kill -SIGINT " + process.pid());
            }
            process.waitFor();
        }
        return process.exitValue();
    }

    @Override
    public void upload(File localFile, String remoteDir, boolean withDecompression, IMsgListener execListener) throws Exception {
        File dir = new File(remoteDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if(withDecompression){
            throw new RuntimeException("LocalExecConnection 解压功能还未完成");
        }

        IOUtils.copy(new FileInputStream(localFile), new FileOutputStream(remoteDir + "/" + localFile.getName()));
    }

    @Override
    public File download(String remotePath, File localFile, IMsgListener execListener) throws Exception {
        execListener.appendLine("本地环境,就不复制文件了");
        return new File(remotePath);
    }

    @Override
    public void close() throws IOException {

    }
}
