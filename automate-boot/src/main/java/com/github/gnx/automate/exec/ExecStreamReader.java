package com.github.gnx.automate.exec;

import com.github.gnx.automate.common.IMsgListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 15:32
 */
public class ExecStreamReader {


    /**
     * 线程池
     * SynchronousQueue 是无缓冲等待队列
     */
    private static ExecutorService pool = new ThreadPoolExecutor(0,
            Integer.MAX_VALUE,
            10L,
            TimeUnit.SECONDS,
            new SynchronousQueue());

    public static ExecStreamReadRunner submit(InputStream inputStream, Charset charset, IMsgListener execListener) {
        ExecStreamReadRunner execStreamReadRunner = new ExecStreamReadRunner(inputStream, charset, execListener);
        pool.execute(execStreamReadRunner);
        return execStreamReadRunner;
    }

    public static class ExecStreamReadRunner implements Runnable {
        private static Logger logger = LoggerFactory.getLogger(ExecStreamReadRunner.class);

        private final InputStream inputStream;
        private final Charset charset;
        private final IMsgListener execListener;

        private CountDownLatch countDownLatch = new CountDownLatch(1);

        private ExecStreamReadRunner(InputStream inputStream, Charset charset, IMsgListener execListener) {
            this.inputStream = inputStream;
            this.charset = charset;
            this.execListener = execListener;
        }


        @Override
        public void run() {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    execListener.append(line);
                }
                logger.info("读取完成");
            } catch (IOException e) {
                //读取超时时  会通过关闭 stream 来结束该进程
                logger.warn("stream read error, {}", e.getMessage());
            } finally {

                try {
                    inputStream.close();
                } catch (Exception e) {

                }

                countDownLatch.countDown();
            }

        }


        public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
            return countDownLatch.await(timeout, unit);
        }

        public void close() {
            //等待超时  关闭 inputStream流
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}