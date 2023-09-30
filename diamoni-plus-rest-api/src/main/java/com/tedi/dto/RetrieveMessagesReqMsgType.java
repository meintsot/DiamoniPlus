package com.tedi.dto;

public class RetrieveMessagesReqMsgType {

    String discussionReference;

    Integer page;

    Integer pageSize;

    public String getDiscussionReference() {
        return discussionReference;
    }

    public void setDiscussionReference(String discussionReference) {
        this.discussionReference = discussionReference;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
