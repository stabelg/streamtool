package com.fabestonia.streamtool.streamtool.handler;

import com.fabestonia.streamtool.streamtool.service.GameService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StringWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, List<WebSocketSession>> sessionPerKey = new ConcurrentHashMap<>();
    private final GameService gameService;

    public StringWebSocketHandler(GameService gameService) {
        this.gameService = gameService;
        gameService.addListeners(this::sendUpdates);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String key = getKey(session);
        sessionPerKey.computeIfAbsent(key, k -> new ArrayList<>()).add(session);

        try {
            session.sendMessage(new TextMessage(gameService.getText(key)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String key = getKey(session);
        sessionPerKey.getOrDefault(key, new ArrayList<>()).remove(session);
    }

    private String getKey(WebSocketSession session) {
        String uri = session.getUri().toString();
        return uri.substring(uri.lastIndexOf('/') + 1);
    }

    private void sendUpdates(String key, String value) {
        List<WebSocketSession> sessions = sessionPerKey.getOrDefault(key, Collections.emptyList());
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(value));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}