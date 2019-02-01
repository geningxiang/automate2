package com.automate.cmd;

/**
 * ExecuteResult.java
 */


public class ExecuteResult {
    private int exitCode;
    private String executeOut;

    public ExecuteResult(int exitCode, String executeOut) {
        this.exitCode = exitCode;
        this.executeOut = executeOut;
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getExecuteOut() {
        return executeOut;
    }
}