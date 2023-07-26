package com.tedi.service;

import com.tedi.auth.UserService;
import com.tedi.dao.BookingDao;
import com.tedi.dao.DiamoniPlusUserDao;
import com.tedi.dao.ReviewsDao;
import com.tedi.dto.*;
import com.tedi.fault.ErrorMessageType;
import com.tedi.fault.ValidationFault;
import com.tedi.mapper.ReviewsMapper;
import com.tedi.model.Booking;
import com.tedi.model.DiamoniPlusUser;
import com.tedi.model.Review;
import com.tedi.validator.ReviewsValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ReviewsService {

    @Inject
    UserService userService;

    @Inject
    ReviewsValidator reviewsValidator;

    @Inject
    DiamoniPlusUserDao diamoniPlusUserDao;

    @Inject
    BookingDao bookingDao;

    @Inject
    ReviewsMapper reviewsMapper;

    @Inject
    ReviewsDao reviewsDao;

    public void submitReview(SubmitReviewReqMsgType param) throws ValidationFault {

        reviewsValidator.validateMissingHostUsernameAndBookingReference(param);
        reviewsValidator.validateEitherHostUsernameOrBookingReferenceIsApplicable(param);

        DiamoniPlusUser tenant = diamoniPlusUserDao.findByUsername(userService.getUser()).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_01_ReviewsService)
        );

        Review review = reviewsMapper.toReview(param);

        if (Objects.nonNull(param.getHostUsername())) {
            submitUserReview(param.getHostUsername(), review, tenant);
        } else {
            submitBookingReview(param.getBookingReference(), review, tenant);
        }
    }

    private void submitBookingReview(String bookingReference, Review review, DiamoniPlusUser tenant) throws ValidationFault {

        Booking booking = bookingDao.findByBookingReference(bookingReference).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_03_ReviewsService)
        );

        review.setBooking(booking);
        review.setTenant(tenant);
        reviewsDao.saveReview(review);
    }

    private void submitUserReview(String hostUsername, Review review, DiamoniPlusUser tenant) throws ValidationFault {

        DiamoniPlusUser host = diamoniPlusUserDao.findByUsername(hostUsername).orElseThrow(
                () -> new ValidationFault(ErrorMessageType.DATA_02_ReviewsService)
        );

        review.setHost(host);
        review.setTenant(tenant);
        reviewsDao.saveReview(review);
    }

    public RetrieveReviewsRespMsgType retrieveReviews(RetrieveReviewsReqMsgType param) throws ValidationFault {

        reviewsValidator.validateEitherHostUsernameOrRentalSpaceReferenceIsRequired(param);
        List<Review> reviews = reviewsDao.retrieveReviews(param);
        return reviewsMapper.toRetrieveReviewsRespMsgType(reviews);
    }
}
