package com.tedi.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchUserProfilesRespMsgType {

    private List<UserProfileResult> userProfileResults = new ArrayList<>();
    private Long totalResults;

    public List<UserProfileResult> getUserProfileResults() {
        return userProfileResults;
    }

    public void setUserProfileResults(List<UserProfileResult> userProfileResults) {
        this.userProfileResults = userProfileResults;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }
}
