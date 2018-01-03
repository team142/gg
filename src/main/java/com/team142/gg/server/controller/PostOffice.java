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
 * @author just1689
 */
public class PostOffice {

    private static final String CONVERSATION_FIELD = "conversation";
    private static final Logger LOG = Logger.getLogger(PostOffice.class.getName());

    public static void handleIncoming(String id, String message) {
        String conversation = JsonUtils.readFieldOrEmptyString(message, CONVERSATION_FIELD);
        postIncomingMessage(id, message, conversation);

    }

    private static void postIncomingMessage(String id, String message, String conversation) {

        Class clazz = ConversationMap.MAP.get(conversation);
        if (clazz == null) {
            LOG.log(Level.SEVERE, "No class for message: {0}", conversation);
            return;
        }

        Message body = (Message) JsonUtils.jsonToObject(message, clazz);
        body.setFrom(id);
        if (body instanceof Runnable) {
            ((Runnable) body).run();
            LOG.log(Level.INFO, "Just ran: {0}", conversation);
            return;
        }

        LOG.log(Level.WARNING, "Could not run message: {0}", conversation);

    }

    public static void sendPlayerAMessage(String playerId, Message message) {
//        LOG.log(Level.INFO, "Sending message to player: {0}, conversation: {1}", new String[]{playerId, message.getConversation()});
        String json = JsonUtils.toJson(message);
        Session session = Server.SESSIONS_ON_SERVER.get(playerId);
        if (session != null) {
            session.getAsyncRemote().sendText(json);
        }
    }

    public static void sendPlayersAMessage(Game game, Message message) {
        game.getPlayers().forEach((player) -> sendPlayerAMessage(player.getId(), message));
    }

}
