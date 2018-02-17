/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.view;

import com.team142.gg.server.controller.MessageManager;
import com.team142.gg.server.controller.ServerManager;
import com.team142.gg.server.model.Server;
import java.io.EOFException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author just1689
 */


import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 *
 * @author just1689
 */
public class GameWebSocket extends TextWebSocketHandler{
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        Thread.sleep(3000); // simulated delay
        TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!");
        session.sendMessage(msg);
    }
}
/*
@ServerEndpoint("/websocket")
public class Websocket {

    @OnOpen
    public void onOpen(Session session) {
        ServerManager.notifyNewConnection(session);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        ServerManager.checkSession(session);
        if (!(t instanceof EOFException)) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "Unknown Error at websocket:", t);
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        String id = session.getId();
        MessageManager.handleIncoming(id, message);
    }

    @OnClose
    public void onClose(Session session) {
        ServerManager.notifyDisconnection(session);

    }

}*/
