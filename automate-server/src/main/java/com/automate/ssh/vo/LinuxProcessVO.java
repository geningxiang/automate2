package com.automate.ssh.vo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/19 21:03
 */
public class LinuxProcessVO {

    /**
     * 进程ID
     */
    private Integer pid;
    /**
     * 父进程ID
     */
    private Integer ppid;

    /**
     * CPU占比
     */
    private Float cpuRate;

    /**
     * 内存占比
     */
    private Float memRate;
    /**
     * 进程属于哪个用户
     */
    private String user;

    /**
     * VSZ 进程使用的虚拟內存量（KB）；
     */
    private Integer vsz;

    /**
     *  该进程占用的固定內存量（KB）（驻留中页的数量）；
     */
    private Integer rss;

    /**
     * 该进程在那個終端上運行（登陸者的終端位置），若與終端無關，則顯示（？）。
     * 若为pts/0等，则表示由网络连接主机进程
     */
    private String tty;

    /**
     * 状态
     *    D 不可中断 Uninterruptible（usually IO）
     *    R 正在运行，或在队列中的进程
     *    S 处于休眠状态
     *    T 停止或被追踪
     *    Z 僵尸进程
     *    W 进入内存交换（从内核2.6开始无效）
     *    X   死掉的进程
     *    < 高优先级
     *    n   低优先级
     *    s   包含子进程
     *    +   位于后台的进程组
     */
    private String stat;


    /**
     * 启动时间(分钟)
     */
    private Integer time;

    /**
     * 具体命令级参数
     */
    private String command;

    /**
     * 参数
     */
    private String args;


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }

    public Float getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(Float cpuRate) {
        this.cpuRate = cpuRate;
    }

    public Float getMemRate() {
        return memRate;
    }

    public void setMemRate(Float memRate) {
        this.memRate = memRate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getVsz() {
        return vsz;
    }

    public void setVsz(Integer vsz) {
        this.vsz = vsz;
    }

    public Integer getRss() {
        return rss;
    }

    public void setRss(Integer rss) {
        this.rss = rss;
    }

    public String getTty() {
        return tty;
    }

    public void setTty(String tty) {
        this.tty = tty;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}
