package com.github.gnx.automate;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/7 23:25
 */
public class Test {

    public static void main(String[] args) throws Exception {

        Test test = (Test) Class.forName("com.github.gnx.automate.Test").getDeclaredConstructor().newInstance();

        test.hello();

        ReentrantLock lock = new ReentrantLock();
    }


    public void hello() {
        System.out.println("hello");
    }
}
