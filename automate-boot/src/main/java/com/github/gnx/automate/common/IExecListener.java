package com.github.gnx.automate.common;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 20:14
 */
public interface IExecListener {


    default void onStart(String step, String msg){

    }

    IExecListener onMsg(CharSequence csq);

    default IExecListener onError(CharSequence csq){
        return this;
    }
}
