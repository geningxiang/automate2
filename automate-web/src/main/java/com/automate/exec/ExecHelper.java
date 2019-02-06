package com.automate.exec;

import com.automate.common.Charsets;
import com.automate.common.utils.SystemUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 15:31
 */
public class ExecHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExecHelper.class);

    /**
     * 管理 用于 CMD 操作 的线程
     */
    private static ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3L, TimeUnit.SECONDS,
            new SynchronousQueue(), new CustomThreadFactory());

    /**
     * 执行命令
     * !该方法会阻塞  直到运行结束或超时
     *
     * @param execCommand
     */
    public static void exec(ExecCommand execCommand) {
        final Process process;
        ExecStreamReader inputReader = null;
        Future<Integer> executeFuture = null;
        try {
            String[] cmds = new String[3];

            if(SystemUtil.isWindows()) {
                cmds[0] = "cmd.exe";
                cmds[1] = "/c";
            } else {
                cmds[0] = "/bin/sh";
                cmds[1] = "-c";
            }

            cmds[2] = execCommand.getCommand();

            logger.debug(StringUtils.join(cmds, " "));

            Map<String, String> envpMap = execCommand.getEnvpMap();
            if(envpMap == null){
                envpMap = System.getenv();
            } else {
                envpMap.putAll(System.getenv());
            }
            String[] evnp = new String[envpMap.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : envpMap.entrySet()) {
                evnp[i++] = entry.getKey() + "=" + entry.getValue();
            }

            process = Runtime.getRuntime().exec(cmds, evnp, execCommand.getDir());
            execCommand.start();

            process.getOutputStream().close();

            inputReader = new ExecStreamReader(process.getInputStream(), execCommand, false);
            pool.execute(inputReader);
            // TODO 如果以 ExecStreamReader 的方式读取 errorStream  当发生超时时 close errorStream 时会阻塞
            //errorReader = new ExecStreamReader(process.getErrorStream(), execCommand, true);
            //pool.execute(errorReader);

            executeFuture = pool.submit(() -> process.waitFor());
            int exitValue = executeFuture.get(execCommand.getTimeout(), execCommand.getUnit());
            if (exitValue != 0) {
                List<String> lines = IOUtils.readLines(process.getErrorStream(), SystemUtil.isWindows() ? Charsets.UTF_GBK : Charsets.UTF_8);
                for (String line : lines) {
                    execCommand.errorRead(line);
                }
            }
            execCommand.end(exitValue);
        } catch (Exception e) {
            execCommand.errorRead("【error by ExecHelper】" + e.getClass().getName());
            // 1 表示 通用未知错误　
            execCommand.end(1);
            logger.error("exec error", e);
        } finally {
            if (executeFuture != null) {
                try {
                    executeFuture.cancel(true);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
            if (inputReader != null) {
                inputReader.close();
            }
        }
    }

    private static class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("CmdHelperThread-" + count.addAndGet(1));
            return t;
        }
    }
}
