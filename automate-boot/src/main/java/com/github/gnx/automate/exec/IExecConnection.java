package com.github.gnx.automate.exec;

import com.github.gnx.automate.common.IMsgListener;

import java.io.Closeable;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/18 23:05
 */
public interface IExecConnection extends Closeable {

    int exec(String cmd, IMsgListener execListener) throws Exception;

    /**
     * 上传文件
     * 假设 localFile = /tmp/a.jar
     *      remoteDir = /var/work
     * 上传后的文件就是 /var/work/a.jar
     * @param localFile 本地文件 (文件夹请先tar、gz)
     * @param remoteDir 远程文件夹
     * @param withDecompression 是否需要解压
     * @param execListener
     * @return
     * @throws Exception
     */
    void upload(File localFile, String remoteDir, boolean withDecompression, IMsgListener execListener) throws Exception;


    /**
     *  下载文件
     *  假设 remotePath = /var/work/a.jar
     *       localDir = /var/tmp/
     *  下载后的文件就是 /var/tmp/a.jar
     * @param remotePath 远程文件夹或文件
     * @param localDir 本地文件夹
     * @param execListener
     * @throws Exception
     */
    File download(String remotePath, File localDir, IMsgListener execListener) throws Exception;

}
