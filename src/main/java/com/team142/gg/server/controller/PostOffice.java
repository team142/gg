/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.messages.base.Message;
import com.team142.gg.server.model.messages.base.ConversationMap;
import com.team142.gg.server.utils.JsonUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 *
 * @author just1689
 */
public class PostOffice {

    public static void handleIncoming(String id, String message) {
        Message messageType = (Message) JsonUtils.jsonToObject(message, Message.class);
        postIncomingMessage(id, message, messageType.getConversation());

    }

    private static void postIncomingMessage(String id, String message, String conversation) {

        Class clazz = ConversationMap.MAP.get(conversation);
        if (clazz == null) {
            Logger.getLogger(PostOffice.class.getName()).log(Level.SEVERE, "No class for message: {0}", conversation);
            return;
        }

        Message body = (Message) JsonUtils.jsonToObject(message, clazz);
        body.setFrom(id);
        if (body instanceof Runnable) {
            ((Runnable) body).run();
            Logger.getLogger(PostOffice.class.getName()).log(Level.INFO, "Just ran: {0}", conversation);
            return;
        }

        Logger.getLogger(PostOffice.class.getName()).log(Level.WARNING, "Could not run message: {0}", conversation);

    }

    public static void sendObjectToPlayer(String playerId, Message message) {
        Logger.getLogger(PostOffice.class.getName()).log(Level.INFO, "Sending message to player: {0}", playerId);
        String json = JsonUtils.toJson(message);
        Session session = Server.SESSIONS_ON_SERVER.get(playerId);
        if (session != null) {
            session.getAsyncRemote().sendText(json);
        }
    }

    static void sendObjectToPlayers(Game game, Message message) {
        game.getPlayers().forEach((player) -> sendObjectToPlayer(player.getId(), message));
    }

}
