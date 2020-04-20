package com.github.gnx.automate.exec;

import com.github.gnx.automate.common.IExecListener;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/20 22:56
 */
public class ExecPrintListener implements IExecListener {

    @Override
    public void onStart(String step, String msg) {

    }

    @Override
    public IExecListener onMsg(CharSequence csq) {
        System.out.println("[onMsg]" + csq);
        return this;
    }

    @Override
    public IExecListener onError(CharSequence csq) {
        System.err.println("[onError]" + csq);
        return this;
    }
}
