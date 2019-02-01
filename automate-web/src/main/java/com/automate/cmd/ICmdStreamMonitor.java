package com.automate.cmd;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 16:17
 */
public interface ICmdStreamMonitor {

    /**
     * 开始
     */
    void onStart(String command);

    /**
     * 有新的行消息
     * @param line
     */
    void onMsg(String line);

    /**
     * 结束
     * @param exitValue
     */
    void onEnd(int exitValue);
}
