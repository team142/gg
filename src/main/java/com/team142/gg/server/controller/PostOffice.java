/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.client.ConversationType;
import com.team142.gg.server.model.client.Message;
import com.team142.gg.server.model.client.MessageJoinGame;
import com.team142.gg.server.model.client.MessageJoinServer;
import com.team142.gg.server.utils.JsonUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author just1689
 */
public class PostOffice {

    public static void handleIncoming(Session session, String message) {
        try {
            //Find the type of message
            Message messageType = JsonUtils.OBJECT_MAPPER.readValue(message, Message.class);
            postIncomingMessage(session, message, messageType.getConversation());
        } catch (IOException ex) {
            Logger.getLogger(PostOffice.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void postIncomingMessage(Session session, String message, String conversation) {
        if (conversation.equals(ConversationType.P_REQUEST_JOIN_SERVER.name())) {
            handleIncomingRequestJoinServer(session, message);
        } else if (conversation.equals(ConversationType.P_REQUEST_JOIN_GAME.name())) {
            handleIncomingRequestJoinGame(session, message);
        }
    }

    private static void handleIncomingRequestJoinServer(Session session, String message) {
        try {
            MessageJoinServer body = JsonUtils.OBJECT_MAPPER.readValue(message, MessageJoinServer.class);
            Server.PLAYERS_ON_SERVER.get(session.getId()).setName(body.getName());
            System.out.println("Player has a name! " + body.getName());
        } catch (IOException ex) {
            Logger.getLogger(PostOffice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void handleIncomingRequestJoinGame(Session session, String message) {
        try {
            MessageJoinGame body = JsonUtils.OBJECT_MAPPER.readValue(message, MessageJoinGame.class);
            Referee.playerJoinsGame(session.getId(), body.getId());
        } catch (IOException ex) {
            Logger.getLogger(PostOffice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
