package com.automate.cmd;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 15:32
 */
public class ExecuetCommand {
    /**
     * 命令
     */
    private String command;
    /**
     * 环境变量
     * 格式为["name1=value1", "name2=value2"]
     */
    private String[] envp;
    /**
     * 指定工作目录
     */
    private File dir;

    private StringBuffer out = new StringBuffer(1024);
    private StringBuffer error = new StringBuffer(1024);

    /**
     * 返回状态码
     */
    private int exitValue = 1;

    /**
     * 监控器
     */
    private ICmdStreamMonitor cmdStreamMonitor;

    /**
     * 默认的超时时间时 1分钟
     */
    private long timeout = 1;
    private TimeUnit unit = TimeUnit.MINUTES;

    public ExecuetCommand(String command) {
        this(command, null, null, null);
    }

    public ExecuetCommand(String command, ICmdStreamMonitor cmdStreamMonitor) {
        this(command, null, null, cmdStreamMonitor);
    }

    public ExecuetCommand(String command, String[] envp, File dir, ICmdStreamMonitor cmdStreamMonitor) {
        this.command = command;
        this.envp = envp;
        this.dir = dir;
        this.cmdStreamMonitor = cmdStreamMonitor;
    }

    public void setTimeOut(TimeUnit unit, long timeout){
        if(unit == null || timeout <= 0){
            throw new IllegalArgumentException(" this timeout is not allow : " + timeout + " / " + unit);
        }
        this.unit = unit;
        this.timeout = timeout;
    }

    public void inputRead(String line) {
        this.out.append(line).append(System.lineSeparator());
        if (this.cmdStreamMonitor != null) {
            this.cmdStreamMonitor.onMsg(line);
        }
    }

    public void errorRead(String line) {
        this.error.append(line).append(System.lineSeparator());
        if (this.cmdStreamMonitor != null) {
            this.cmdStreamMonitor.onMsg(line);
        }
    }

    public void start() {
        if (this.cmdStreamMonitor != null) {
            this.cmdStreamMonitor.onStart(this.command);
        }
    }

    public void end(int exitValue) {
        this.exitValue = exitValue;
        if (this.cmdStreamMonitor != null) {
            this.cmdStreamMonitor.onEnd(exitValue);
        }
    }

    public String getCommand() {
        return command;
    }

    public String[] getEnvp() {
        return envp;
    }

    public File getDir() {
        return dir;
    }

    public StringBuffer getOut() {
        return out;
    }

    public StringBuffer getError() {
        return error;
    }

    public int getExitValue() {
        return exitValue;
    }

    public long getTimeout() {
        return timeout;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public ICmdStreamMonitor getCmdStreamMonitor() {
        return cmdStreamMonitor;
    }
}
