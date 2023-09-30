package com.tedi.dto;

public class SaveMessageReqMsgType {

    private String discussionReference;

    private MessageType message;

    public String getDiscussionReference() {
        return discussionReference;
    }

    public void setDiscussionReference(String discussionReference) {
        this.discussionReference = discussionReference;
    }

    public MessageType getMessage() {
        return message;
    }

    public void setMessageType(MessageType messageType) {
        this.message = messageType;
    }
}
