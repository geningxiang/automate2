package com.automate.websocket;

import com.alibaba.fastjson.JSON;
import com.automate.common.SessionUser;
import com.automate.contants.CommonContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/7 17:49
 */
public class MyHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 握手前
     *
     * @param request
     * @param response
     * @param webSocketHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            SessionUser sessionUser = (SessionUser) ((ServletServerHttpRequest) request).getServletRequest().getSession().getAttribute(CommonContants.SESSION_USER_KEY);
            if (sessionUser == null) {
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return false;
            }
            attributes.put(CommonContants.SESSION_USER_KEY, sessionUser);
        } else {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
        }
        return super.beforeHandshake(request, response, webSocketHandler, attributes);
    }


    /**
     * 握手后
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception e) {
        super.afterHandshake(request, response, wsHandler, e);
    }

}