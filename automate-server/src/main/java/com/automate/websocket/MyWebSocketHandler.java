package com.automate.websocket;

import com.alibaba.fastjson.JSON;
import com.automate.common.SessionUser;
import com.automate.contants.CommonContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/7 17:49
 */
public class MyWebSocketHandler implements WebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(MyWebSocketHandler.class);



    // 连接 就绪时
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        log.info("connect websocket success.......{}", session.getId());

        SessionUser sessionUser = (SessionUser)session.getAttributes().get(CommonContants.SESSION_USER_KEY);
        log.debug(JSON.toJSONString(sessionUser));
    }

    // 处理前端发送的消息信息
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//获取消息体
        String msg = message.getPayload().toString();
//        if (msg.startsWith("java.nio.HeapByteBuffer")) {
//            System.out.println("浏览器发送的心跳包");
//            return;
//        }
        System.out.println(msg);

        session.sendMessage(new TextMessage(msg));

    }

    // 处理传输时异常
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

//log.info("数据传输异常： " + exception.getMessage());
    }

    // 关闭 连接时
    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {

        log.info("connect websocket closed.......");

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}