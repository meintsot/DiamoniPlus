package com.tedi.mapper;

import com.tedi.dto.RetrieveReviewsRespMsgType;
import com.tedi.dto.ReviewResultType;
import com.tedi.dto.SubmitReviewReqMsgType;
import com.tedi.model.Review;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ReviewsMapper {

    public Review toReview(SubmitReviewReqMsgType param) {

        Review review = new Review();
        review.setRating(param.getRating());
        review.setDescription(param.getDescription());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        return review;
    }

    public RetrieveReviewsRespMsgType toRetrieveReviewsRespMsgType(List<Review> reviews) {
        
        RetrieveReviewsRespMsgType response = new RetrieveReviewsRespMsgType();
        response.getReviewResults().addAll(toReviewResultType(reviews));
        return response;
    }

    private List<ReviewResultType> toReviewResultType(List<Review> reviews) {

        return reviews.stream().map(this::toReviewResultType).toList();
    }

    private ReviewResultType toReviewResultType(Review review) {

        ReviewResultType reviewResultType = new ReviewResultType();
        reviewResultType.setRating(review.getRating());
        reviewResultType.setDescription(review.getDescription());
        reviewResultType.setAuthor(review.getTenant().getUsername());
        if (Objects.nonNull(review.getTenant().getAvatar())) {
            reviewResultType.setAuthorImageIdentification(review.getTenant().getAvatar().getBinaryIdentification());
        }

        return reviewResultType;
    }
}
