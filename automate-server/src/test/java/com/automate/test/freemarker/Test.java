package com.automate.test.freemarker;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2018/12/30 20:36
 */
public class Test {


    public static void main(String[] args) throws IOException, TemplateException {
        Configuration config = new Configuration(Configuration.VERSION_2_3_28);

//        config.setDirectoryForTemplateLoading(new File("D:/work"));
//        Template t = config.getTemplate("server.xml", "");

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        String templateContent= FileUtils.readFileToString(new File("D:\\idea-workspace\\Automate\\src\\test\\resources\\template\\tomcat\\config/server.xml"), "UTF-8");
        stringLoader.putTemplate("myTemplate",templateContent);

        config.setTemplateLoader(stringLoader);

        Template t = config.getTemplate("myTemplate","utf-8");

        Writer out = new StringWriter(2048);
        Map<String, Object> data = new HashMap();
        data.put("port", 8080);
        t.process(data, out);

        System.out.println(out.toString());

    }

}
