package ai.hajimebot.service;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName WebSocket
 * @Description TODO
 * @Version 1.0
 */
@Component
@Slf4j
@ServerEndpoint("/ws/admin")
public class WebSocketAdminService {

    //client session
    private Session session;
    /**
     * User ID
     */
    private String userId;

    /**
     * It is used to store the MyWebSocket object corresponding to each client.
     */
    private static CopyOnWriteArraySet<WebSocketAdminService> webSockets = new CopyOnWriteArraySet<>();
    /**
     * Used to store user information for wire connections
     */
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();


    /**
     * onOpen
     */
    @OnOpen
    public void onOpen(Session session) {
        webSockets.add(this);
        this.session = session;
        log.info("[websocket message] connection success");
        session.getAsyncRemote().sendText("websocket connection success...");
    }
    /**
     * onClose
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            log.info("[websocket message] conn close, total: {}", webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * message
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("[websocket message] Client message is received: " + JSONUtil.toJsonStr(message));

    }

    /**
     * error
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("error:" + error.getMessage());
    }


    public void sendAllMessage(String message) {
        log.info("[websocket message] Broadcast messages");
        for (WebSocketAdminService webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

