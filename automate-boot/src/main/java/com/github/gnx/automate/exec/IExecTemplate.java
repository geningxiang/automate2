package com.github.gnx.automate.exec;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 22:29
 */
public interface IExecTemplate {


    <T> T execute(ExecWorker<T> execWorker) throws Exception;

}
