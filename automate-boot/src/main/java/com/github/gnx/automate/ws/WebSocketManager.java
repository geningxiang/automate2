package com.github.gnx.automate.ws;

import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/29 22:10
 */
@Component
public class WebSocketManager {

    private static ConcurrentHashMap<String, WebSocketUser> currentUserMap = new ConcurrentHashMap(256);

    public static void onOpen(Session session) {
        WebSocketUser webSocketUser = new WebSocketUser(session);
        currentUserMap.put(webSocketUser.getId(), webSocketUser);
    }

    public static void onClose(Session session) {
        currentUserMap.remove(session.getId());
    }


}
