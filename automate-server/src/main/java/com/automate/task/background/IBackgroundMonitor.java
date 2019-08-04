package com.automate.task.background;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 后台任务监控接口
 * @author: genx
 * @date: 2019/2/2 10:28
 */
public interface IBackgroundMonitor {

    /**
     * 任务新加入等待队列
     * @param uniqueId
     * @param name
     * @param waitingSize
     */
    void onWait(long uniqueId, String name, int waitingSize);

    /**
     * 任务开始执行
     * @param uniqueId
     * @param name
     * @param runningSize
     * @param waitingSize
     */
    void onStart(long uniqueId, String name, int runningSize, int waitingSize);

    /**
     * 任务完成 ！不一定是成功     也可能是 error 或 cancel
     * @param uniqueId
     * @param name
     * @param status {@link BackgroundStatus}
     * @param runningSize
     * @param waitingSize
     */
    void onComplete(long uniqueId, String name, String status, int runningSize, int waitingSize);


}
