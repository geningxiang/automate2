package com.github.gnx.automate.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/5 14:44
 */
@ServerEndpoint("/ws/{userToken}")
@Component
public class WebSocketController {
    private Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    private ConcurrentHashMap<Integer, Session> currentUserMap = new ConcurrentHashMap(256);

    @OnOpen
    public void onOpen(Session session, @PathParam("userToken") String userToken) throws IOException {

        logger.debug("webSocket op open : {}", userToken);

        //TODO 用户认证
        if(StringUtils.isNotBlank(userToken)){

            int userId = NumberUtils.toInt(userToken, 1);

            currentUserMap.put(userId, session);

        } else {
            session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "请登录"));
        }
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {

        logger.info("用户退出:当前在线人数为:");
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("用户消息,报文:" + message);


    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }


}
