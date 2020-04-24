package com.github.gnx.automate.exec;

import com.github.gnx.automate.common.IMsgListener;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/20 22:56
 */
public class MsgPrintListener implements IMsgListener {


    @Override
    public IMsgListener append(CharSequence csq) {
        System.out.print(csq);
        return this;
    }

}
