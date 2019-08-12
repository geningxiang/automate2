package com.automate.task.background;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 8:53
 */
public abstract class AbstractBackgroundTask implements Runnable {

    private static final AtomicLong INDEX = new AtomicLong(0);

    /**
     * 后台任务的 唯一ID
     */
    protected final long uniqueId;

    /**
     * 后台任务的 锁列表   简单的根据 String 来锁
     */
    protected final BackgroundLock.Builder lockBuilder;

    protected final Map<String, String> environmentPathMap = new HashMap(64);

    /**
     *
     * @param lockBuilder
     */
    public AbstractBackgroundTask(BackgroundLock.Builder lockBuilder) {
        this.uniqueId = INDEX.incrementAndGet();
        this.lockBuilder = lockBuilder;

    }


    private boolean cancel = false;
    private long startTime;
    private long endTime;

    /**
     * 任务状态
     */
    private BackgroundStatus status = BackgroundStatus.waiting;

    /**
     * 具体的状态内容  以下状态才会有值
     * acquire      正在申请的锁名称
     * channel      取消原因
     * error        错误信息
     */
    private String statusMsg;

    /**
     * 名称
     * @return
     */
    public abstract String getName();


    public void updateStatus(BackgroundStatus status) {
        updateStatus(status, null);
    }

    public void updateStatus(BackgroundStatus status, String msg) {
        this.status = status;
        this.statusMsg = msg;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public BackgroundStatus getStatus() {
        return status;
    }

    /**
     * 是否已取消
     * @return
     */
    public boolean isCancel() {
        return this.cancel;
    }

    public BackgroundLock.Builder getLockBuilder() {
        return lockBuilder;
    }


}
