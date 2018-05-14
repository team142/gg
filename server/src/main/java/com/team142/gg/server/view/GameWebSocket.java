/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.view;

import com.team142.gg.server.controller.MessageManager;
import com.team142.gg.server.controller.ServerManager;
import com.team142.gg.server.model.Server;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.EOFException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author just1689
 */
public class GameWebSocket implements WebSocketHandler {

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String id = session.getId();
        MessageManager.handleIncoming(id, message.getPayload().toString());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        ServerManager.notifyNewConnection(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        ServerManager.checkSession(session);
        if (!(exception instanceof EOFException)) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "Unknown Error at websocket:", exception);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        ServerManager.notifyDisconnection(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
