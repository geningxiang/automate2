package com.github.gnx.automate.exec;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/7/26 18:08
 */
public class ExecException extends Exception {

    private final int exitStatus;

    public ExecException(int exitStatus, String error) {
        super(error);
        this.exitStatus = exitStatus;
    }


    public int getExitStatus() {
        return exitStatus;
    }
}
