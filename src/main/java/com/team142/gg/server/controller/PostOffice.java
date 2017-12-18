/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.messages.ConversationType;
import com.team142.gg.server.model.messages.Message;
import com.team142.gg.server.model.messages.MessageJoinGame;
import com.team142.gg.server.model.messages.MessageJoinServer;
import static com.team142.gg.server.utils.JsonUtils.OBJECT_MAPPER;
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
        String id = session.getId();
        try {
            //Find the type of message
            Message messageType = OBJECT_MAPPER.readValue(message, Message.class);
            postIncomingMessage(id, message, messageType.getConversation());
        } catch (IOException ex) {
            Logger.getLogger(PostOffice.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void postIncomingMessage(String id, String message, String conversation) {
        if (conversation.equals(ConversationType.P_REQUEST_JOIN_SERVER.name())) {
            handleIncomingRequestJoinServer(id, message);
        } else if (conversation.equals(ConversationType.P_REQUEST_JOIN_GAME.name())) {
            handleIncomingRequestJoinGame(id, message);
        }
    }

    private static void handleIncomingRequestJoinServer(String id, String message) {
        try {
            MessageJoinServer body = OBJECT_MAPPER.readValue(message, MessageJoinServer.class);
            ServerAdmin.playerHasAName(id, body.getName());
            System.out.println("Player has a name! " + body.getName());
        } catch (IOException ex) {
            Logger.getLogger(PostOffice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void handleIncomingRequestJoinGame(String id, String message) {
        try {
            MessageJoinGame body = OBJECT_MAPPER.readValue(message, MessageJoinGame.class);
            Referee.playerJoinsGame(id, body.getId());
        } catch (IOException ex) {
            Logger.getLogger(PostOffice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void sendObjectToPlayer(String playerId, Object object) {
        try {
            String json = OBJECT_MAPPER.writeValueAsString(object);
            Server.SESSIONS_ON_SERVER.get(playerId).getAsyncRemote().sendText(json);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(PostOffice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
