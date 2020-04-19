package com.github.gnx.automate.common;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 20:14
 */
public interface IExecListener {


    void onStart(String step, String msg);

    IExecListener onMsg(CharSequence csq);

    IExecListener onError(CharSequence csq);
}
