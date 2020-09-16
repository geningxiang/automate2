package com.github.gnx.automate.common.webmvc;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/16 23:29
 */
@Configuration
public class JsonConverterConfig implements WebMvcConfigurer {

    /**
     * 使用fastjson代替jackson
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        /*

         先把JackSon的消息转换器删除.
         备注: (1)源码分析可知，返回json的过程为:
                    Controller调用结束后返回一个数据对象，for循环遍历conventers，找到支持application/json的HttpMessageConverter，然后将返回的数据序列化成json。
                    具体参考org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor的writeWithMessageConverters方法
               (2)由于是list结构，我们添加的fastjson在最后。因此必须要将jackson的转换器删除，不然会先匹配上jackson，导致没使用fastjson
        */
        for (int i = converters.size() - 1; i >= 0; i--) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                converters.remove(i);
            }
        }
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        //自定义fastjson配置

        FastJsonConfig config = new FastJsonConfig();

        config.setSerializerFeatures(

                // 是否输出值为null的字段,默认为false,我们将它打开
                SerializerFeature.WriteMapNullValue,

                // 将Collection类型字段的字段空值输出为[]
                SerializerFeature.WriteNullListAsEmpty,

                // 将字符串类型字段的空值输出为空字符串
//                SerializerFeature.WriteNullStringAsEmpty,

                // 将数值类型字段的空值输出为0
//                SerializerFeature.WriteNullNumberAsZero,

                SerializerFeature.WriteDateUseDateFormat,

                // 禁用循环引用
                SerializerFeature.DisableCircularReferenceDetect

        );

        fastJsonHttpMessageConverter.setFastJsonConfig(config);


        // 添加支持的MediaTypes;不添加时默认为*/*,也就是默认支持全部

        // 但是MappingJackson2HttpMessageConverter里面支持的MediaTypes为application/json

        // 参考它的做法, fastjson也只添加application/json的MediaType

        List<MediaType> fastMediaTypes = new ArrayList<>();

        fastMediaTypes.add(new MediaType("application", "json", StandardCharsets.UTF_8));
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        converters.add(fastJsonHttpMessageConverter);
    }
}
