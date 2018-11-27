package com.team142.gg.server.model.messages.outgoing.rendered;

import com.team142.gg.server.model.messages.base.ConversationType;
import com.team142.gg.server.model.messages.base.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageSpeech extends Message {

    @Getter
    private String text;

    public MessageSpeech(String text) {
        this.setConversation(ConversationType.S_SHARE_SPEECH.name());
        this.text = text;
    }

}
