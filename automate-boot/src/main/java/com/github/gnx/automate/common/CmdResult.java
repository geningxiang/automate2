package com.github.gnx.automate.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018-01-24 13:47
 */
public class CmdResult {

    private String cmd;

    /**
     * 执行的结果状态
     */
    private int exitStatus = -1;

    /**
     * 命令执行的结果
     */
    private List<String> result = new ArrayList<>(256);

    /**
     * 命令执行的错误
     */
    private String error;

    public CmdResult(String cmd) {
        this.cmd = cmd;
    }

    public int getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    public List<String> getResult() {
        return result;
    }

    public void addResult(String lineStr) {
        this.result.add(lineStr);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCmd() {
        return cmd;
    }

    public boolean isSuccess(){
        return exitStatus == 0;
    }
}
