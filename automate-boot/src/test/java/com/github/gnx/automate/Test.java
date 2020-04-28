package com.github.gnx.automate;

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


    }


    public void hello() {
        System.out.println("hello");
    }
}
