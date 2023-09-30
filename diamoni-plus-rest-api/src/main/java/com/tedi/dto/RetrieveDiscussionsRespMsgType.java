package com.tedi.dto;

import java.util.ArrayList;
import java.util.List;

public class RetrieveDiscussionsRespMsgType {

    List<DiscussionType> discussions = new ArrayList<>();

    public List<DiscussionType> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<DiscussionType> discussions) {
        this.discussions = discussions;
    }
}
