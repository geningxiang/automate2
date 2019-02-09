package com.automate.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *  不想新开端口， 而且这个项目不会有太多并发， 所以没有使用 netty
 *
 * Configuration 不能用自动注入， 否则 test会报错， 所以暂时 放在 spring-mvc.xml 手动注入
 * @author: genx
 * @date: 2019/2/7 17:49
 */
//@Configuration
@EnableWebSocket
@EnableWebMvc
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //这里的url要与页面的url一致
        webSocketHandlerRegistry.addHandler(myHandler(), "/webSocket").addInterceptors(new MyHandshakeInterceptor());
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }
}