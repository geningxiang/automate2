package com.github.gnx.automate.exec.ssh;

import com.github.gnx.automate.common.IMsgListener;
import com.jcraft.jsch.SftpProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @Author:luzhengxian Description 通过ssh远程连接进行文件上传时的工具接口实现方法
 * @Date:
 */
public class SftpProgressMonitorImpl implements SftpProgressMonitor {

    private static Logger logger = LoggerFactory.getLogger(SftpProgressMonitorImpl.class);

    private long size;
    private long currentSize = 0;

    private double rate = 0;

    private boolean endFlag = false;

    private IMsgListener msgLineWriter;

    public SftpProgressMonitorImpl() {

    }

    public SftpProgressMonitorImpl(IMsgListener msgLineWriter) {
        this.msgLineWriter = msgLineWriter;
    }

    @Override
    public void init(int mode, String srcFile, String dstDir, long size) {

        String msg = "文件开始上传：[" + srcFile + "]-->[" + dstDir + "]" + ",文件大小：" + size + ",参数：" + mode;
        // inputstream上传时  size = -1
        logger.debug(msg);
        this.size = size;

        if (msgLineWriter != null) {
            msgLineWriter.appendLine(msg);
        }
    }

    @Override
    public boolean count(long count) {
        currentSize += count;
        String msg;
        if (this.size > 0) {
            double currentRate = (double) currentSize / this.size;
            if (currentRate - rate > 0.05) {
                rate = currentRate;
                msg = "上传进度：" + currentSize + "/" + size + " " + new BigDecimal(rate).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%";
                logger.debug(msg);
                if (msgLineWriter != null) {
                    msgLineWriter.appendLine(msg);
                }
            }
        } else {
            msg = "上传进度：已上传:" + currentSize;
            logger.debug(msg);
            if (msgLineWriter != null) {
                msgLineWriter.appendLine(msg);
            }
        }
        return true;
    }

    @Override
    public void end() {
        logger.debug("文件上传结束");
        if (msgLineWriter != null) {
            msgLineWriter.appendLine("文件上传结束");
        }
        rate = 1;
        endFlag = true;
    }

    public boolean isSuccess() {
        return endFlag;
    }
}
