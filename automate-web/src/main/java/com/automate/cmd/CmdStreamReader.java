package com.automate.cmd;

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
public class CmdStreamReader implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private InputStream inputStream;
    private final ExecuetCommand execuetCommand;
    private final boolean isError;

    public CmdStreamReader(final InputStream inputStream, final ExecuetCommand execuetCommand, boolean isError) {
        this.inputStream = inputStream;
        this.execuetCommand = execuetCommand;
        this.isError = isError;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,  SystemUtil.isWindows() ? Charsets.UTF_GBK : Charsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (this.isError) {
                    execuetCommand.errorRead(line);
                } else {
                    execuetCommand.inputRead(line);
                }
            }
            inputStream = null;
        } catch (IOException e) {
            //读取超时时  会通过关闭 stream 来结束该进程
            logger.info("Runtime.getRuntime().exec stream read error, timeout ?");
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