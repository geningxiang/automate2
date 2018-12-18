package com.automate.ssh;

import com.jcraft.jsch.SftpProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:luzhengxian
 * Description 通过ssh远程连接进行文件上传时的工具接口实现方法
 * @Date:
 */
public class SftpProgressMonitorImpl implements SftpProgressMonitor{

    private static Logger logger = LoggerFactory.getLogger(SftpProgressMonitorImpl.class);

    private long size;
    private long currentSize=0;
    private boolean endFlag=false;

    @Override
    public void init(int op, String srcFile, String dstDir, long size) {
        logger.debug("文件开始上传：["+srcFile+"]-->["+dstDir+"]"+",文件大小："+size+",参数："+op);
        this.size=size;
    }

    @Override
    public boolean count(long count) {
        currentSize+=count;
        logger.debug("上传数量："+currentSize);
        return true;
    }

    @Override
    public void end() {
        logger.debug("文件上传结束");
        endFlag=true;
    }

    public boolean isSuccess(){
        return endFlag;
    }
}
