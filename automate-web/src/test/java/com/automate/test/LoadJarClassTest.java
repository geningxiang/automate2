package com.automate.test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

import org.junit.Test;


public class LoadJarClassTest {

    @Test
    public void testLoadClass() throws Exception {
        /*动态加载指定类*/
        File file = new File("D:\\idea-workspace\\Automate2\\automate-web\\src\\main");//类路径(包文件上一层)
        URL url = file.toURI().toURL();
        System.out.println(url);
        ClassLoader loader = new URLClassLoader(new URL[]{url});//创建类加载器
        //import com.sun.org.apache.bcel.internal.util.ClassLoader;
        //ClassLoader classLoader = new ClassLoader(new String[]{""});//类路径
        Class<?> cls = loader.loadClass("com.automate.build.MyCat");//加载指定类，注意一定要带上类的包名



        System.out.println(cls);


//        Object obj = cls.newInstance();//初始化一个实例

//        Method method = cls.getMethod("printString", String.class, String.class);//方法名和对应的参数类型
//        Object o = method.invoke(obj, "chen", "leixing");//调用得到的上边的方法method
//        System.out.println(String.valueOf(o));//输出"chenleixing"
//
//        /*动态加载指定jar包调用其中某个类的方法*/
//        file = new File("D:/test/commons-lang3.jar");//jar包的路径
//        url = file.toURI().toURL();
//        loader = new URLClassLoader(new URL[]{url});//创建类加载器
//        cls = loader.loadClass("org.apache.commons.lang3.StringUtils");//加载指定类，注意一定要带上类的包名
//        method = cls.getMethod("center", String.class, int.class, String.class);//方法名和对应的各个参数的类型
//        o = method.invoke(null, "chen", Integer.valueOf(10), "0");//调用得到的上边的方法method(静态方法，第一个参数可以为null)
//        System.out.println(String.valueOf(o));//输出"000chen000","chen"字符串两边各加3个"0"字符串
    }

}