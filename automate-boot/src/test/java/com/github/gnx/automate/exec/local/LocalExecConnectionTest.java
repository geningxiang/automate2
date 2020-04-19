package com.github.gnx.automate.exec.local;

import com.github.gnx.automate.common.IExecListener;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/19 22:49
 */
class LocalExecConnectionTest {

    @Test
    public void test() throws Exception {

        LocalExecConnection execConnection = new LocalExecConnection();


        int exitValue = execConnection.exec("ping www.baidu.com -n 20", new IExecListener() {
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
                return this;
            }
        });

        System.out.println(exitValue);


        Thread.sleep(3000000);

        execConnection.close();

    }

}