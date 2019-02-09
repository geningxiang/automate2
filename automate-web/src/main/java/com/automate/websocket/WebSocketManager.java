package com.automate.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/2/7 18:20
 */
public class WebSocketManager {
    /**
     * 保存 websocket连接
     */
    private static final Map<String, WebSocketSession> WEB_SOCKET_SESSION_MAP = new ConcurrentHashMap<>(256);

    public static void onOpen(){

    }

    public static void onClose(){

    }
}
