package com.automate.ssh;

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

    @Override
    public void init(int mode, String srcFile, String dstDir, long size) {

        // inputstream上传时  size = -1
        logger.debug("文件开始上传：[{}]-->[{}]" + ",文件大小：{},参数：{}", srcFile, dstDir, size, mode);
        this.size = size;
    }

    @Override
    public boolean count(long count) {
        currentSize += count;
        if (size > 0) {
            if (currentSize / size - rate > 0.05) {
                rate = currentSize / size;
                logger.debug("上传进度：{}/{} {}%", currentSize, size, new BigDecimal(rate).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        } else {
            logger.debug("上传进度：已上传{}", currentSize);
        }

        return true;
    }

    @Override
    public void end() {
        logger.debug("文件上传结束");
        rate = 1;
        endFlag = true;
    }

    public boolean isSuccess() {
        return endFlag;
    }
}
