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
@ServerEndpoint("/websocket")
public class Websocket {

    @OnOpen
    public void onOpen(Session session) {
        ServerManager.notifyNewConnection(session);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        if (!session.isOpen()) {
            Logger.getLogger(Server.class.getName()).log(Level.INFO, "Player disconnected");
            ServerManager.playerDisconnects(session.getId());
        }

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

}
