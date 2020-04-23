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
     * @param remoteDir
     * @param withDecompression 是否需要解压
     * @param execListener
     * @return
     * @throws Exception
     */
    void upload(File localFile, String remoteDir, boolean withDecompression, IExecListener execListener) throws Exception;

    void download(String remotePath, File localFile, IExecListener execListener) throws Exception;

}
