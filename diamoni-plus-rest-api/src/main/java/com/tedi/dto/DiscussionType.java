package com.tedi.dto;

public class DiscussionType {

    private String discussionReference;

    private String username;

    private String avatarReference;

    public DiscussionType(String discussionReference, String username, String avatarReference) {
        this.discussionReference = discussionReference;
        this.username = username;
        this.avatarReference = avatarReference;
    }

    public String getDiscussionReference() {
        return discussionReference;
    }

    public void setDiscussionReference(String discussionReference) {
        this.discussionReference = discussionReference;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarReference() {
        return avatarReference;
    }

    public void setAvatarReference(String avatarReference) {
        this.avatarReference = avatarReference;
    }
}
