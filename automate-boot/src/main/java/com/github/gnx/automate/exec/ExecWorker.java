package com.github.gnx.automate.exec;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 22:21
 */
public abstract class ExecWorker<T>{


    public abstract T doWork(IExecConnection execConnection);
}
