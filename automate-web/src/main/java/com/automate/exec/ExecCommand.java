package com.automate.exec;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/1 15:32
 */
public class ExecCommand {
    /**
     * 命令
     */
    private List<String> commands;
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
    private IExecStreamMonitor cmdStreamMonitor;

    /**
     * 默认的超时时间时 3分钟
     */
    private long timeout = 3;
    private TimeUnit unit = TimeUnit.MINUTES;

    public ExecCommand(String command) throws IllegalAccessException {
        this(ImmutableList.of(command), null, null, null);
    }

    public ExecCommand(String command, IExecStreamMonitor cmdStreamMonitor) throws IllegalAccessException {
        this(ImmutableList.of(command), null, null, cmdStreamMonitor);
    }

    public ExecCommand(List<String> commands, IExecStreamMonitor cmdStreamMonitor) throws IllegalAccessException {
        this(commands, null, null, cmdStreamMonitor);
    }

    public ExecCommand(List<String> commands, String[] envp, File dir, IExecStreamMonitor cmdStreamMonitor) throws IllegalAccessException {
        this.commands = ExecFilter.filter(commands);
        this.envp = envp;
        this.dir = dir;
        this.cmdStreamMonitor = cmdStreamMonitor;
    }

    public void setTimeOut(TimeUnit unit, long timeout) {
        if (unit == null || timeout <= 0) {
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
            this.cmdStreamMonitor.onStart(StringUtils.join(this.commands, " && "));
        }
    }

    public void end(int exitValue) {
        this.exitValue = exitValue;
        if (this.cmdStreamMonitor != null) {
            this.cmdStreamMonitor.onEnd(exitValue);
        }
    }

    public List<String> getCommands() {
        return commands;
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

    public IExecStreamMonitor getCmdStreamMonitor() {
        return cmdStreamMonitor;
    }
}
