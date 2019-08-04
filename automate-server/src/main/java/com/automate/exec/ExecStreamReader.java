package com.automate.exec;

import com.automate.common.Charsets;
import com.automate.common.utils.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 15:32
 */
public class ExecStreamReader implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private InputStream inputStream;
    private final ExecCommand execCommand;
    private final boolean isError;

    public ExecStreamReader(final InputStream inputStream, final ExecCommand execCommand, boolean isError) {
        this.inputStream = inputStream;
        this.execCommand = execCommand;
        this.isError = isError;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, this.execCommand.getCharset());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                logger.debug(line);
                if (this.isError) {
                    execCommand.errorRead(line);
                } else {
                    execCommand.inputRead(line);
                }
            }
            inputStream = null;
        } catch (IOException e) {
            //读取超时时  会通过关闭 stream 来结束该进程
            logger.info("stream read error, {}", e.getMessage());
        }
    }

    public void close(){
        if(inputStream != null){
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error("stream close failed", e);
            }
        }
    }

}