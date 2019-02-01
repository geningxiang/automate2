package com.automate.cmd;

import com.automate.common.Charsets;
import com.automate.common.utils.SystemUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 15:31
 */
public class CmdHelper {
    private static final Logger logger = LoggerFactory.getLogger(CmdHelper.class);

    /**
     * 管理 用于 CMD 操作 的线程
     */
    private static ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3L, TimeUnit.SECONDS,
            new SynchronousQueue(), new CustomThreadFactory());

    /**
     * 执行命令
     * !该方法会阻塞  直到运行结束或超时
     *
     * @param execuetCommand
     */
    public static void exec(ExecuetCommand execuetCommand) {
        final Process process;
        CmdStreamReader inputReader = null;
        Future<Integer> executeFuture = null;
        try {
            logger.info(execuetCommand.getCommand());
            process = Runtime.getRuntime().exec(execuetCommand.getCommand());
            execuetCommand.start();
            process.getOutputStream().close();

            inputReader = new CmdStreamReader(process.getInputStream(), execuetCommand, false);
            pool.execute(inputReader);
            // TODO 如果以 CmdStreamReader 的方式读取 errorStream  当发生超时时 close errorStream 时会阻塞
            //errorReader = new CmdStreamReader(process.getErrorStream(), execuetCommand, true);
            //pool.execute(errorReader);

            executeFuture = pool.submit(() -> process.waitFor());
            int exitValue = executeFuture.get(execuetCommand.getTimeout(), execuetCommand.getUnit());
            if (exitValue != 0) {
                List<String> lines = IOUtils.readLines(process.getErrorStream(), SystemUtil.isWindows() ? Charsets.UTF_GBK : Charsets.UTF_8);
                for (String line : lines) {
                    execuetCommand.errorRead(line);
                }
            }
            execuetCommand.end(exitValue);
        } catch (Exception e) {
            execuetCommand.errorRead("【error by CmdHelper】" + e.getClass().getName());
            // 1 表示 通用未知错误　
            execuetCommand.end(1);
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
