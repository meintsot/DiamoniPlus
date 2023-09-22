package com.tedi.dao;

import com.tedi.dto.RetrieveReviewsReqMsgType;
import com.tedi.model.Review;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ReviewsDao {

    @PersistenceContext
    EntityManager em;

    public void saveReview(Review review) {
        em.persist(review);
    }

    public List<Review> retrieveReviews(RetrieveReviewsReqMsgType param) {

        int startIndex = (param.getPage() - 1) * param.getPageSize();

        TypedQuery<Review> query;
        if (Objects.nonNull(param.getUsername())) {
            query = retrieveReviewsByUsername(param.getUsername());
        } else {
            query = retrieveReviewsByRentalSpaceReference(param.getRentalSpaceReference());
        }
        return query.setFirstResult(startIndex).setMaxResults(param.getPageSize()).getResultList();
    }

    private TypedQuery<Review> retrieveReviewsByUsername(String username) {
        return em.createNamedQuery("Review.findByHost_Username", Review.class).setParameter("username", username);
    }

    private TypedQuery<Review> retrieveReviewsByRentalSpaceReference(String rentalSpaceReference) {
        return em.createNamedQuery("Review.findByBooking_RentalSpace_RentalSpaceReference", Review.class)
                .setParameter("rentalSpaceReference", rentalSpaceReference);
    }
}
