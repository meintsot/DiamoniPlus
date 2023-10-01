package com.tedi.dao;

import com.tedi.dto.RetrieveReviewsReqMsgType;
import com.tedi.model.Review;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            query = retrieveReviewsByHostUsername(param.getUsername());
        } else {
            query = retrieveReviewsByRentalSpaceReference(param.getRentalSpaceReference());
        }
        return query.setFirstResult(startIndex).setMaxResults(param.getPageSize()).getResultList();
    }

    private TypedQuery<Review> retrieveReviewsByHostUsername(String username) {
        return em.createNamedQuery("Review.findByHost_Username", Review.class).setParameter("username", username);
    }

    public Optional<Review> retrieveReviewsByTenantUsername(String username) {
        try {
            TypedQuery<Review> query = em.createNamedQuery("Review.findByTenant_Username", Review.class)
                    .setParameter("username", username);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private TypedQuery<Review> retrieveReviewsByRentalSpaceReference(String rentalSpaceReference) {
        return em.createNamedQuery("Review.findByBooking_RentalSpace_RentalSpaceReference", Review.class)
                .setParameter("rentalSpaceReference", rentalSpaceReference);
    }
}
