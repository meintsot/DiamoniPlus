package com.tedi.dto;

public class DiscussionType {

    private String discussionReference;

    private String username;

    public DiscussionType(String discussionReference, String username) {
        this.discussionReference = discussionReference;
        this.username = username;
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
}
