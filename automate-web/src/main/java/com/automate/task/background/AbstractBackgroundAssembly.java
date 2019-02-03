package com.automate.task.background;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 8:53
 */
public abstract class AbstractBackgroundAssembly implements Runnable {

    private static final AtomicLong INDEX = new AtomicLong(0);

    /**
     * 后台任务的 唯一ID
     */
    private final long uniqueId;

    /**
     * 后台任务的 锁列表   简单的根据 String 来锁
     */
    private final String[] locks;
    private int lockIndex;

    public AbstractBackgroundAssembly(Set<String> locks){
        this.uniqueId = INDEX.incrementAndGet();
        if(locks != null && locks.size() > 0) {
            String[] temp = new String[locks.size()];
            int i = 0;
            for (String lock : locks) {
                temp[i++] = lock;
            }
            if(i > 1) {
                //!! 非常重要  必须要做一次排序    否则会出现 不同任务 互锁的情况
                Arrays.sort(temp);
            }
            this.locks = temp;
        } else {
            this.locks = null;
        }

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

    public String[] getLocks() {
        return locks;
    }

    public BackgroundStatus getStatus() {
        return status;
    }

    /**
     * 是否已取消
     * @return
     */
    public boolean isCancel(){
        return this.cancel;
    }

    public int getLockIndex() {
        return lockIndex;
    }

    public void setLockIndex(int lockIndex) {
        this.lockIndex = lockIndex;
    }
}
