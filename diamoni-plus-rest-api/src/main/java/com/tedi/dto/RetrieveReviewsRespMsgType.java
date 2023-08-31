package com.tedi.dto;

import java.util.List;

public class RetrieveReviewsRespMsgType {

    private List<ReviewResultType> reviewResults;
    private Double averageReviews;

    public List<ReviewResultType> getReviewResults() {
        return reviewResults;
    }

    public void setReviewResults(List<ReviewResultType> reviewResults) {
        this.reviewResults = reviewResults;
    }

    public Double getAverageReviews() {
        return averageReviews;
    }

    public void setAverageReviews(Double averageReviews) {
        this.averageReviews = averageReviews;
    }
}
