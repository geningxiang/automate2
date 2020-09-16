package com.github.gnx.automate.exec.local;

import com.github.gnx.automate.common.IMsgListener;
import org.junit.jupiter.api.Test;

import java.io.File;

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


        int exitValue = execConnection.exec("ping www.baidu.com -n 20", new IMsgListener() {

            @Override
            public IMsgListener append(CharSequence csq) {
                System.out.println("[append]" + csq);
                return this;
            }

        });

        System.out.println(exitValue);


        Thread.sleep(3000000);

        execConnection.close();

    }


    @Test
    public void makeDirs() {

        File file = new File("d:/1/1.txt");

        File dir = file.getParentFile();

        System.out.println(dir.getAbsolutePath());

    }
}