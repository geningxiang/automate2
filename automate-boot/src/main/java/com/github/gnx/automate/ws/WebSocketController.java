package com.github.gnx.automate.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

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

    @OnOpen
    public void onOpen(Session session, @PathParam("userToken") String userToken) throws IOException {

        logger.debug("webSocket op open : {}", userToken);

        //TODO 用户认证
        if(StringUtils.isNotBlank(userToken)){

            int userId = NumberUtils.toInt(userToken, 1);

            WebSocketManager.onOpen(session);

        } else {
            session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "请登录"));
        }
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        logger.debug("onClose:{}", session.toString());
        WebSocketManager.onClose(session);
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("用户消息,报文:" + message);

        session.getAsyncRemote().sendText("返回:"+message);

        try{
            JSONObject json = JSON.parseObject(message);
            String op = json.getString("op");

            if(OpEnum.Subscribe.name().equals(op)){

            } else {

            }


        }catch (Exception e){

        }

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
