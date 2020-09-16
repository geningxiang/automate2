package com.automate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/2 21:45
 */
public class SpringTest {

    public static void main(String[] args) {

//        System.out.println(Thread.currentThread().getId() + "-" + Thread.currentThread().getName());

//        org.springframework.data.querydsl.SimpleEntityPathResolver;

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        System.out.println(context);


    }

}
