/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.messages;

import static com.team142.gg.server.model.messages.ConversationType.S_CHANGE_VIEW;

/**
 *
 * @author just1689
 */
public class MessageChangeView extends Message {

    private final ViewType view;

    public MessageChangeView(ViewType view) {
        this.view = view;
        setConversation(S_CHANGE_VIEW.name());

    }

}
