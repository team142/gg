/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Repository;
import com.team142.gg.server.model.messages.base.Message;
import com.team142.gg.server.model.messages.base.ConversationMap;
import com.team142.gg.server.utils.JsonUtils;
import java.nio.ByteBuffer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 * @author just1689
 */
public class MessageManager {

    private static final String CONVERSATION_FIELD = "conversation";
    private static final Logger LOG = Logger.getLogger(MessageManager.class.getName());

    public static void handleIncoming(String id, String message) {
        if (message.equals("1")) {
            Repository.PLAYERS_ON_SERVER.get(id).getTickerComms().pong();
            return;
        }
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
            return;
        }

        LOG.log(Level.WARNING, "Could not run message: {0}", conversation);

    }

    public static void sendPlayersAMessage(Game game, Message message) {
        ByteBuffer bytes = JsonUtils.toByteBuffer(message);
        game.getPlayers().forEach((player) -> sendPlayerAMessage(Repository.SESSIONS_ON_SERVER.get(player.getId()), bytes));
    }

    public static void sendPlayerAMessage(String playerId, Message message) {
        ByteBuffer bytes = JsonUtils.toByteBuffer(message);
        Session session = Repository.SESSIONS_ON_SERVER.get(playerId);
        sendPlayerAMessage(session, bytes);

    }

    public static void sendPlayerAMessage(Session session, ByteBuffer byteBuffer) {
        if (session != null) {
            session.getAsyncRemote().sendBinary(byteBuffer);
        }
    }

    /**
     *
     * @param session
     * @param json
     * @deprecated 
     * WARNING: try to use *sendPlayerAMessage(Session session, ByteBuffer
     * byteBuffer)* instead as the overhead of using Strings is really bad
     * (String pool etc). This will be left in for now as it may be useful
     * and we would like communications to happen in this class.
     *
     */
    public static void sendPlayerAMessage(Session session, String json) {
        if (session != null) {
            session.getAsyncRemote().sendText(json);
        }
    }
}
