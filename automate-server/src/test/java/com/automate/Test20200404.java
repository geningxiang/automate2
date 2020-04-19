package com.automate;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/4 0:29
 */
public class Test20200404 {

    public static void main(String[] args) {

        new Cat();

    }


    static class Animal{
        public Animal(){
            System.out.println("Animal");
        }
    }

    static class Cat extends Animal{
        public Cat(){
            super();
            System.out.println("Cat");
        }
    }
}
