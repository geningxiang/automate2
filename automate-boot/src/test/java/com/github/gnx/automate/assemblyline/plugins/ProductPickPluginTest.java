package com.github.gnx.automate.assemblyline.plugins;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/25 13:43
 */
class ProductPickPluginTest {

    @Test
    public void test() {
        Properties properties = System.getProperties();

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {

            System.out.println(entry);


        }



    }

}