package com.github.gnx.automate.common;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 20:14
 */
public interface IMsgListener {


    IMsgListener append(CharSequence csq);

    default IMsgListener appendLine(CharSequence csq) {
        return this.append(csq).append(System.lineSeparator());
    }

}
