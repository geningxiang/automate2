package com.automate.exec;

import org.apache.commons.lang3.StringUtils;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/2 0:25
 */
public class ExecFilterTest {

    public static void main(String[] args) {
        String command = "cd  d:/  ";
        String fn;
        String param = null;
        int i = command.indexOf(" ");
        if(i > 0){
            fn = command.substring(0, i);
            param = StringUtils.trim(command.substring(i + 1));
        } else {
            fn = command;
        }

        System.out.println(fn);

        System.out.println(param);
    }
}