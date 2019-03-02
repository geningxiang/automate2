package com.automate.common;

import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import com.alibaba.druid.support.logging.Log;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/28 21:45
 */
@Component
public class MyDruidDataSourceStatLogger implements DruidDataSourceStatLogger {

    @Override
    public void log(DruidDataSourceStatValue druidDataSourceStatValue) {
        System.out.println(druidDataSourceStatValue.getUrl());
    }

    @Override
    public void configFromProperties(Properties properties) {

    }

    @Override
    public void setLogger(Log log) {

    }

    @Override
    public void setLoggerName(String s) {

    }
}
