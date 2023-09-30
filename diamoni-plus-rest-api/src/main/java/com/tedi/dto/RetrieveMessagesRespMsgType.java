package com.tedi.dto;

import java.util.ArrayList;
import java.util.List;

public class RetrieveMessagesRespMsgType {

    List<MessageType> messages = new ArrayList<>();

    public List<MessageType> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageType> messages) {
        this.messages = messages;
    }
}
