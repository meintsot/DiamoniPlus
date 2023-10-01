package com.tedi.mapper;

import com.tedi.dto.*;
import com.tedi.model.*;
import com.tedi.utils.DataUtils;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class DiscussionsMapper {

    public List<MessageType> toMessageType(List<Message> messages) {

        return messages.stream().map(this::toMessageType).toList();
    }

    private MessageType toMessageType(Message message) {
        MessageType messageType = new MessageType();

        messageType.setSender(message.getSender().getUsername());
        messageType.setReceiver(message.getReceiver().getUsername());
        messageType.setMessageId(message.getMessageId());
        messageType.setMessageText(message.getMessageText());
        messageType.setDeleted(message.getDeleted());
        messageType.setCreatedAt(DataUtils.fromLocalDateTimeToString(message.getCreatedAt()));

        return messageType;
    }
}
