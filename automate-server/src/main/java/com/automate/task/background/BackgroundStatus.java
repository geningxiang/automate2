package com.automate.task.background;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 状态
 *
 * @author: genx
 * @date: 2019/2/2 14:39
 */
public enum BackgroundStatus {
    /**
     * 等待执行
     */
    waiting,
    /**
     * 申请中  需要获得锁的许可
     */
    acquire,
    /**
     * 执行中
     */
    running,
    /**
     * 已取消
     */
    cancel,
    /**
     * 发生错误
     */
    error,
    /**
     * 执行成功
     */
    success

}
