/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.controller;

import com.team142.gg.server.model.Game;
import com.team142.gg.server.model.Server;
import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import com.team142.gg.server.model.messages.MessageJoinGame;
import com.team142.gg.server.model.messages.MessageJoinServer;
import com.team142.gg.server.model.messages.MessageListOfPlayers;
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
        if (ConversationType.P_REQUEST_JOIN_SERVER.name().equals(conversation)) {
            MessageJoinServer body = (MessageJoinServer) JsonUtils.jsonToObject(message, MessageJoinServer.class);
            Logger.getLogger(PostOffice.class.getName()).log(Level.INFO, "Message is join server: {0}", body.getName());
            ServerAdmin.handle(id, body);
        } else if (ConversationType.P_REQUEST_JOIN_GAME.name().equals(conversation)) {
            MessageJoinGame body = (MessageJoinGame) JsonUtils.jsonToObject(message, MessageJoinGame.class);
            Logger.getLogger(PostOffice.class.getName()).log(Level.INFO, "Message is join game: {0}", id);
            Referee.handle(id, body);
        }
    }

    public static void sendObjectToPlayer(String playerId, Message message) {
        Logger.getLogger(PostOffice.class.getName()).log(Level.INFO, "Sending message to player: {0}", playerId);
        String json = JsonUtils.toJson(message);
        Session session = Server.SESSIONS_ON_SERVER.get(playerId);
        if (session != null) {
            session.getAsyncRemote().sendText(json);
        }
    }

    static void sendObjectToPlayers(Game game, MessageListOfPlayers messageListOfPlayers) {
        game.getPlayers().forEach((player) -> sendObjectToPlayer(player.getId(), messageListOfPlayers));
    }

}
