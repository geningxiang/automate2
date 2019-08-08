package com.automate.exec;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 16:17
 */
public interface IExecStreamMonitor {

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
     *
     * @param line
     */
    void onError(String line);

    /**
     * 结束
     * @param exitValue
     */
    void onEnd(int exitValue);
}
