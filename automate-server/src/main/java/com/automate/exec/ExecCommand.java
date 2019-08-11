package com.automate.exec;

import com.automate.common.Charsets;
import com.automate.common.SystemConfig;
import com.automate.common.utils.EnvironmentPathUtil;
import com.automate.common.utils.SystemUtil;
import com.google.common.collect.ImmutableList;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
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
    private String command;
    /**
     * 环境变量
     * 格式为["name1=value1", "name2=value2"]
     */
    private Map<String, String> envpMap;
    /**
     * 指定工作目录
     */
    private File dir;

    private StringBuffer msg = new StringBuffer(1024);
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
     * 默认的超时时间时 10分钟
     */
    private int timeout = 10;
    private TimeUnit unit = TimeUnit.MINUTES;

    private Charset charset;

    public ExecCommand(String command) throws IllegalAccessException {
        this(ImmutableList.of(command), null, null, null);
    }

    public ExecCommand(String command, IExecStreamMonitor cmdStreamMonitor) throws IllegalAccessException {
        this(ImmutableList.of(command), null, null, cmdStreamMonitor);
    }

    public ExecCommand(List<String> commands, IExecStreamMonitor cmdStreamMonitor) throws IllegalAccessException {
        this(commands, null, null, cmdStreamMonitor);
    }

    public ExecCommand(List<String> commands, Map<String, String> envpMap, File dir, IExecStreamMonitor cmdStreamMonitor) throws IllegalAccessException {
        this.command = ExecFormatter.format(commands);
        this.envpMap = envpMap;
        this.dir = dir;
        this.cmdStreamMonitor = cmdStreamMonitor;

        if(SystemUtil.isWindows()){
            charset = Charsets.UTF_GBK;
        } else {
            charset = Charsets.UTF_8;
        }
    }

    public void setTimeOut(TimeUnit unit, int timeout) {
        if (unit == null || timeout <= 0) {
            throw new IllegalArgumentException(" this timeout is not allow : " + timeout + " / " + unit);
        }
        this.unit = unit;
        this.timeout = timeout;
    }

    public void inputRead(String line) {
        this.msg.append(line).append(System.lineSeparator());
        if (this.cmdStreamMonitor != null) {
            this.cmdStreamMonitor.onMsg(line);
        }
    }

    public void errorRead(String line) {
        this.error.append(line).append(System.lineSeparator());
        if (this.cmdStreamMonitor != null) {
            this.cmdStreamMonitor.onError(line);
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

    public Map<String, String> getEnvpMap() {
        //从 ThreadLocal 中的 读取环境变量
        Map<String, String> map = EnvironmentPathUtil.get();
        if (this.envpMap != null && this.envpMap.size() > 0) {
            map.putAll(this.envpMap);
        }
        return map;
    }

    public File getDir() {
        return dir;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }

    /**
     * msg + error
     * @return
     */
    public StringBuffer getOut() {
        StringBuffer out = new StringBuffer(this.msg);
        out.append(this.error);
        return out;
    }

    public StringBuffer getMsg() {
        return msg;
    }

    public StringBuffer getError() {
        return error;
    }

    public int getExitValue() {
        return exitValue;
    }

    public int getTimeout() {
        return timeout;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public IExecStreamMonitor getCmdStreamMonitor() {
        return cmdStreamMonitor;
    }

    public void setCmdStreamMonitor(IExecStreamMonitor cmdStreamMonitor) {
        this.cmdStreamMonitor = cmdStreamMonitor;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
