package com.github.gnx.automate.exec;

import com.github.gnx.automate.common.IExecListener;

import java.io.Closeable;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/18 23:05
 */
public interface IExecConnection extends Closeable {

    int exec(String cmd, IExecListener execListener) throws Exception;

    /**
     *
     * @param localFile
     * @param remoteDir 远程目标的文件夹路径  为了判断要不要创建
     * @param fileName 上传后的名称
     * @param execListener
     * @return
     * @throws Exception
     */
    void upload(File localFile, String remoteDir, String fileName, IExecListener execListener) throws Exception;

    void download(String remotePath, File localFile, IExecListener execListener) throws Exception;

}
