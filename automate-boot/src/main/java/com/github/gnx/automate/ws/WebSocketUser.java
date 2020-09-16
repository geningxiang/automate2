package com.github.gnx.automate.ws;

import javax.websocket.Session;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/4/29 22:13
 */
public class WebSocketUser {

    private final String id;

    private final Session session;

    private Map<String, Long> subscribedMap;

    public WebSocketUser(Session session) {
        this.id = session.getId();
        this.session = session;
    }

    public String getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public Map<String, Long> getSubscribedMap() {
        return subscribedMap;
    }
}
